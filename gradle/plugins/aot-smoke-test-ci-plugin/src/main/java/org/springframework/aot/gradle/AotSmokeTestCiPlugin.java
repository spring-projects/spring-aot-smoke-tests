/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aot.gradle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.gradle.api.GradleException;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskProvider;

/**
 * {@link Plugin} for AOT smoke test CI.
 *
 * @author Andy Wilkinson
 */
public class AotSmokeTestCiPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		NamedDomainObjectContainer<SmokeTests> smokeTests = project.getObjects()
			.domainObjectContainer(SmokeTests.class);
		project.getExtensions().add("smokeTests", smokeTests);
		TaskProvider<Sync> syncWorkflows = project.getTasks().register("syncGitHubActionsWorkflows", Sync.class);
		syncWorkflows.configure((sync) -> {
			sync.into(".github/workflows");
			syncFromClasspath("smoke-test.yml", sync);
			syncFromClasspath("smoke-test-jvm.yml", sync);
			syncFromClasspath("smoke-test-native.yml", sync);
			syncFromClasspath("validate-gradle-wrapper.yml", sync);
			syncFromClasspath("warm-caches.yml", sync);
		});
		CronSchedule cronSchedule = new CronSchedule();
		smokeTests.configureEach((tests) -> {
			String warmCachesSchedule = cronSchedule.warmCaches();
			String runTestsSchedule = cronSchedule.runTests();
			TaskProvider<Exec> describeSmokeTestsForBranch = project.getTasks()
				.register("describeSmokeTestsFor" + tests.getName(), Exec.class);
			describeSmokeTestsForBranch.configure((task) -> {
				task.setWorkingDir(new File(tests.getLocation()));
				task.commandLine("./gradlew", "describeSmokeTests", "--no-scan");
			});
			TaskProvider<GenerateGitHubActionsWorkflows> generateWorkflowsForBranch = project.getTasks()
				.register("generateGitHubActionsWorkflowsFor" + tests.getName(), GenerateGitHubActionsWorkflows.class);
			generateWorkflowsForBranch.configure((task) -> {
				task.dependsOn(describeSmokeTestsForBranch);
				task.getSpringBootGeneration().set(tests.getName());
				if (tests.getBranch() != null) {
					task.getGitBranch().set(tests.getBranch());
				}
				task.getSmokeTests().set(project.provider(() -> loadSmokeTests(tests.getLocation())));
				task.getWarmCachesCronSchedule().set(warmCachesSchedule);
				task.getCronSchedule().set(runTestsSchedule);
			});
			syncWorkflows.configure((sync) -> sync.from(generateWorkflowsForBranch));
			cronSchedule.nextBatch();
		});
	}

	private List<SmokeTest> loadSmokeTests(String location) {
		File[] smokeTests = new File(location + "/build/smoke-tests").listFiles();
		return Stream.of(smokeTests).map(this::load).map(SmokeTest::from).toList();
	}

	private Properties load(File file) {
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream(file)) {
			properties.load(input);
			return properties;
		}
		catch (IOException ex) {
			throw new GradleException("Failed to load smoke test properties from '" + file + "'", ex);
		}
	}

	private void syncFromClasspath(String name, Sync sync) {
		sync.from(sync.getProject().getResources().getText().fromUri(getClass().getClassLoader().getResource(name)),
				(spec) -> spec.rename((temp) -> name));
	}

	static final class CronSchedule {

		private int minute;

		private int hour;

		private CronSchedule() {
			this(0, 0);
		}

		private CronSchedule(int minute, int hour) {
			this.minute = minute;
			this.hour = hour;
		}

		String warmCaches() {
			return asString();
		}

		String runTests() {
			CronSchedule offsetSchedule = new CronSchedule(this.minute, this.hour);
			offsetSchedule.nextBatch();
			return offsetSchedule.asString();
		}

		private String asString() {
			return "%d %d * * *".formatted(this.minute, this.hour);
		}

		void nextBatch() {
			this.minute += 10;
			if (this.minute == 60) {
				this.minute = 0;
				this.hour += 1;
			}
		}

	}

}
