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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/**
 * Task to generate the GitHub Actions workflows for the smoke tests.
 *
 * @author Andy Wilkinson
 */
public abstract class GenerateGitHubActionsWorkflows extends DefaultTask {

	private static final String GITHUB_REPOSITORY = "spring-projects/spring-aot-smoke-tests";

	@OutputDirectory
	public abstract DirectoryProperty getOutputDirectory();

	@Input
	public abstract ListProperty<SmokeTest> getSmokeTests();

	@Input
	public abstract Property<String> getGitBranch();

	@Input
	public abstract Property<String> getCronSchedule();

	@Input
	public abstract Property<String> getSpringBootGeneration();

	public GenerateGitHubActionsWorkflows() {
		getGitBranch().convention(getSpringBootGeneration());
		getOutputDirectory().convention(getSpringBootGeneration()
			.flatMap((generation) -> getProject().getLayout().getBuildDirectory().dir("workflows/" + generation)));
	}

	@TaskAction
	void generateWorkflows() {
		getProject().delete(getOutputDirectory());
		getSmokeTests().get().forEach(this::generateWorkflow);
	}

	void generateWorkflow(SmokeTest smokeTest) {
		File workflowFile = getOutputDirectory()
			.file(getSpringBootGeneration().get() + "-" + smokeTest.group() + "-" + smokeTest.name() + ".yml")
			.get()
			.getAsFile();
		workflowFile.getParentFile().mkdirs();
		String workflowName = getSpringBootGeneration().get() + " | " + name(smokeTest.group()) + " Smoke Tests | "
				+ name(smokeTest.name());
		try (PrintWriter writer = new PrintWriter(new FileWriter(workflowFile))) {
			writer.println("name: " + workflowName);
			writer.println("on:");
			writer.println("  schedule:");
			writer.println("    - cron : '" + getCronSchedule().get() + "'");
			writer.println("  workflow_dispatch:");
			writer.println("jobs:");
			smokeTest.tests().forEach((test) -> {
				writer.println("  " + jobId(smokeTest.name(), test.taskName()) + ":");
				writer.println("    name: " + name(smokeTest.name() + " " + test.taskName()));
				writer.println("    uses: ./.github/workflows/smoke-test-%s.yml"
					.formatted(test.taskName().startsWith("native") ? "native" : "jvm"));
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      checkout_repository: " + GITHUB_REPOSITORY);
				writer.println("      checkout_ref: " + getGitBranch().get());
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: " + test.taskName());
				if (test.expectedToFail()) {
					writer.println("      expected_to_fail: true");
				}
				if (test.javaVersion() != null) {
					writer.println("      java_version: " + test.javaVersion());
				}
			});
		}
		catch (IOException ex) {
			throw new GradleException("Failed to write workflow file '" + workflowFile + "'", ex);
		}
	}

	private String jobId(String smokeTestName, String taskName) {
		StringBuilder output = new StringBuilder();
		output.append(smokeTestName.replace("-", "_"));
		output.append("_");
		for (char c : taskName.toCharArray()) {
			if (Character.isUpperCase(c)) {
				output.append("_");
				output.append(Character.toLowerCase(c));
			}
			else {
				output.append(c);
			}
		}
		return output.toString();
	}

	private String name(String input) {
		StringBuilder output = new StringBuilder(input.length());
		char previous = ' ';
		for (char c : input.replace("-", " ").toCharArray()) {
			if (previous == ' ') {
				output.append(Character.toUpperCase(c));
			}
			else {
				if (Character.isUpperCase(c)) {
					output.append(' ');
				}
				output.append(c);
			}
			previous = c;
		}
		return output.toString();
	}

}
