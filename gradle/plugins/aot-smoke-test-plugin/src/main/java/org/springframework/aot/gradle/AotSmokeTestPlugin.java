/*
 * Copyright 2022 the original author or authors.
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

import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.avast.gradle.dockercompose.ComposeExtension;
import com.avast.gradle.dockercompose.ComposeSettings;
import io.spring.javaformat.gradle.SpringJavaFormatPlugin;
import org.graalvm.buildtools.gradle.NativeImagePlugin;
import org.graalvm.buildtools.gradle.dsl.GraalVMExtension;
import org.graalvm.buildtools.gradle.dsl.GraalVMReachabilityMetadataRepositoryExtension;
import org.graalvm.buildtools.gradle.tasks.BuildNativeImageTask;
import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.Directory;
import org.gradle.api.file.RegularFile;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.testing.Test;
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile;

import org.springframework.aot.gradle.dsl.AotSmokeTestExtension;
import org.springframework.aot.gradle.tasks.AppTest;
import org.springframework.aot.gradle.tasks.DescribeSmokeTests;
import org.springframework.aot.gradle.tasks.StartApplication;
import org.springframework.aot.gradle.tasks.StartJvmApplication;
import org.springframework.aot.gradle.tasks.StartNativeApplication;
import org.springframework.aot.gradle.tasks.StopApplication;
import org.springframework.boot.gradle.plugin.SpringBootPlugin;
import org.springframework.boot.gradle.tasks.bundling.BootJar;

/**
 * {@link Plugin} for an AOT smoke test project. Configures an {@code appTest} source set
 * and tasks for running the contained tests against the application running on the JVM
 * and as a native image.
 *
 * @author Andy Wilkinson
 * @author Moritz Halbritter
 */
public class AotSmokeTestPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getPlugins().withType(JavaPlugin.class, (javaPlugin) -> project.getPlugins()
				.withType(SpringBootPlugin.class, (bootPlugin) -> configureBootJavaProject(project)));
	}

	private void configureBootJavaProject(Project project) {
		AotSmokeTestExtension extension = project.getExtensions().create("aotSmokeTest", AotSmokeTestExtension.class,
				project);
		extension.getWebApplication().convention(false);
		JavaPluginExtension javaExtension = project.getExtensions().getByType(JavaPluginExtension.class);
		SourceSet appTest = javaExtension.getSourceSets().create("appTest");
		javaExtension.setSourceCompatibility(JavaVersion.VERSION_17);
		javaExtension.setTargetCompatibility(JavaVersion.VERSION_17);
		if (project.hasProperty("fromMavenLocal")) {
			String fromMavenLocal = project.property("fromMavenLocal").toString();
			Stream<String> includedGroups = Stream.of(fromMavenLocal.split(","));
			project.getRepositories().mavenLocal(
					(mavenLocal) -> mavenLocal.content((content) -> includedGroups.forEach(content::includeGroup)));
		}
		project.getRepositories().mavenCentral();
		project.getRepositories().maven((repo) -> {
			repo.setName("Spring Milestone");
			repo.setUrl("https://repo.spring.io/milestone");
		});
		project.getRepositories().maven((repo) -> {
			repo.setName("Spring Snapshot");
			repo.setUrl("https://repo.spring.io/snapshot");
		});
		configureAppTests(project, extension, appTest);
		configureTests(project);
		configureKotlin(project, javaExtension);
		configureJavaFormat(project);
		Configuration smokeTests = project.getConfigurations().create("smokeTests");
		TaskProvider<DescribeSmokeTests> describeSmokeTests = project.getTasks().register("describeSmokeTests",
				DescribeSmokeTests.class);
		describeSmokeTests.configure((task) -> {
			task.getOutputDirectory().set(project.getLayout().getBuildDirectory().dir(task.getName()));
			task.setAppTests(appTest.getAllSource());
			task.setTests(javaExtension.getSourceSets().getByName(SourceSet.TEST_SOURCE_SET_NAME).getAllSource());
		});
		project.artifacts((artifacts) -> artifacts.add(smokeTests.getName(), describeSmokeTests));
		DependencyHandler dependencies = project.getRootProject().getDependencies();
		dependencies.add("smokeTests",
				dependencies.project(Map.of("path", project.getPath(), "configuration", smokeTests.getName())));
	}

	private void configureAppTests(Project project, AotSmokeTestExtension extension, SourceSet appTest) {
		configureJvmAppTests(project, appTest, extension);
		configureNativeAppTests(project, appTest, extension);
	}

	private void configureTests(Project project) {
		project.getTasks().named(JavaPlugin.TEST_TASK_NAME, Test.class).configure((test) -> {
			test.systemProperty("spring.aot.enabled", "true");
			test.setDescription("Runs the test suite with AOT generated artifacts.");
			test.useJUnitPlatform();
		});
	}

	private void configureJavaFormat(Project project) {
		project.getPlugins().withType(SpringJavaFormatPlugin.class, (javaFormat) -> project.getGradle()
				.getStartParameter().getExcludedTaskNames().addAll(Set.of("checkFormatAot", "checkFormatAotTest")));
	}

	private void configureKotlin(Project project, JavaPluginExtension javaExtension) {
		project.getTasks().withType(KotlinJvmCompile.class).configureEach((kotlinCompile) -> kotlinCompile
				.getKotlinOptions().setJvmTarget(javaExtension.getTargetCompatibility().toString()));
	}

	private void configureJvmAppTests(Project project, SourceSet aotTest, AotSmokeTestExtension extension) {
		Provider<RegularFile> archiveFile = project.getTasks().named(SpringBootPlugin.BOOT_JAR_TASK_NAME, BootJar.class)
				.flatMap(BootJar::getArchiveFile);
		configureTasks(project, aotTest, ApplicationType.JVM, archiveFile, extension);
	}

	private void configureNativeAppTests(Project project, SourceSet aotTest, AotSmokeTestExtension extension) {
		project.getPlugins().withType(NativeImagePlugin.class, (nativeImagePlugin) -> {
			GraalVMExtension graalVMExtension = project.getExtensions().getByType(GraalVMExtension.class);
			graalVMExtension.getAgent().getTasksToInstrumentPredicate().set((task) -> false);
			GraalVMReachabilityMetadataRepositoryExtension metadataRepositoryExtension = ((ExtensionAware) graalVMExtension)
					.getExtensions().getByType(GraalVMReachabilityMetadataRepositoryExtension.class);
			String reachabilityMetadataVersion = (String) project.getProperties().get("reachabilityMetadataVersion");
			if (reachabilityMetadataVersion != null) {
				metadataRepositoryExtension.getVersion().set(reachabilityMetadataVersion);
			}
			String reachabilityMetadataUrl = (String) project.getProperties().get("reachabilityMetadataUrl");
			if (reachabilityMetadataUrl != null) {
				metadataRepositoryExtension.getUri().set(URI.create(reachabilityMetadataUrl));
			}
			Provider<RegularFile> nativeImage = project.getTasks()
					.named(NativeImagePlugin.NATIVE_COMPILE_TASK_NAME, BuildNativeImageTask.class)
					.flatMap(BuildNativeImageTask::getOutputFile);
			configureTasks(project, aotTest, ApplicationType.NATIVE, nativeImage, extension);
		});
	}

	private TaskProvider<AppTest> configureTasks(Project project, SourceSet appTest, ApplicationType type,
			Provider<RegularFile> nativeImage, AotSmokeTestExtension extension) {
		Provider<Directory> outputDirectory = project.getLayout().getBuildDirectory()
				.dir(type.name().toLowerCase() + "App");
		TaskProvider<? extends StartApplication> startTask = createStartApplicationTask(project, type, nativeImage,
				outputDirectory, extension);
		TaskProvider<StopApplication> stopTask = createStopApplicationTask(project, type, startTask);
		TaskProvider<AppTest> appTestTask = createAppTestTask(project, appTest, type, startTask, stopTask);
		configureDockerComposeIfNecessary(project, type, startTask, appTestTask, stopTask);
		return appTestTask;
	}

	private void configureDockerComposeIfNecessary(Project project, ApplicationType type,
			TaskProvider<? extends StartApplication> startTask, TaskProvider<AppTest> appTestTask,
			TaskProvider<StopApplication> stopTask) {
		if (!project.file("docker-compose.yml").canRead()) {
			return;
		}
		project.getPlugins().apply("com.avast.gradle.docker-compose");
		ComposeExtension compose = project.getExtensions().getByType(ComposeExtension.class);
		ComposeSettings composeSettings = compose.nested(type.name().toLowerCase());
		String composeUpTaskName = composeSettings.getNestedName() + "ComposeUp";
		String composeDownTaskName = composeSettings.getNestedName() + "ComposeDown";
		project.getTasks().named(composeUpTaskName)
				.configure((composeUp) -> composeUp.finalizedBy(composeDownTaskName));
		startTask.configure((start) -> {
			start.dependsOn(composeUpTaskName);
			start.getInternalEnvironment().putAll(environment(project, composeSettings));
		});
		appTestTask
				.configure((appTest) -> appTest.getInternalEnvironment().putAll(environment(project, composeSettings)));
		project.getTasks().named(composeDownTaskName).configure((composeDown) -> composeDown.mustRunAfter(stopTask));
	}

	private Provider<Map<String, String>> environment(Project project, ComposeSettings settings) {
		return project.provider(() -> {
			Map<String, String> environment = new HashMap<>();
			settings.getServicesInfos().forEach((serviceName, service) -> {
				String name = serviceName.toUpperCase(Locale.ENGLISH);
				environment.put(name + "_HOST", service.getHost());
				service.getTcpPorts().forEach(
						(source, target) -> environment.put(name + "_PORT_" + source, Integer.toString(target)));
			});
			return environment;
		});
	}

	private TaskProvider<StopApplication> createStopApplicationTask(Project project, ApplicationType type,
			TaskProvider<? extends StartApplication> startTask) {
		String taskName = switch (type) {
			case JVM -> "stopApp";
			case NATIVE -> "stopNativeApp";
		};
		TaskProvider<StopApplication> stopTask = project.getTasks().register(taskName, StopApplication.class,
				(stop) -> {
					stop.getPidFile().set(startTask.flatMap(StartApplication::getPidFile));
					stop.setDescription("Stops the " + type.description + " application.");
				});
		startTask.configure((start) -> start.finalizedBy(stopTask));
		return stopTask;
	}

	private TaskProvider<? extends StartApplication> createStartApplicationTask(Project project, ApplicationType type,
			Provider<RegularFile> applicationBinary, Provider<Directory> outputDirectory,
			AotSmokeTestExtension extension) {
		String taskName = switch (type) {
			case JVM -> "startApp";
			case NATIVE -> "startNativeApp";
		};
		return project.getTasks().register(taskName, type.startTaskType, (start) -> {
			start.getApplicationBinary().set(applicationBinary);
			start.getOutputDirectory().set(outputDirectory);
			start.setDescription("Starts the " + type.description + " application.");
			start.getWebApplication().convention(extension.getWebApplication());
		});
	}

	private TaskProvider<AppTest> createAppTestTask(Project project, SourceSet source, ApplicationType type,
			TaskProvider<? extends StartApplication> startTask, TaskProvider<StopApplication> stopTask) {
		String taskName = switch (type) {
			case JVM -> "appTest";
			case NATIVE -> "nativeAppTest";
		};
		TaskProvider<AppTest> appTestTask = project.getTasks().register(taskName, AppTest.class, (task) -> {
			task.dependsOn(startTask);
			task.useJUnitPlatform();
			task.setTestClassesDirs(source.getOutput().getClassesDirs());
			task.setClasspath(source.getRuntimeClasspath());
			task.getInputs().file(startTask.flatMap(StartApplication::getApplicationBinary))
					.withPropertyName("applicationBinary");
			task.systemProperty("org.springframework.aot.smoketest.standard-output",
					startTask.get().getOutputFile().get().getAsFile().getAbsolutePath());
			task.finalizedBy(stopTask);
			task.setDescription("Runs the app test suite against the " + type.description + " application.");
			task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
			task.dependsOn(startTask);
		});
		stopTask.configure((stop) -> stop.mustRunAfter(appTestTask));
		project.getTasks().named(JavaBasePlugin.CHECK_TASK_NAME).configure((check) -> check.dependsOn(appTestTask));
		return appTestTask;
	}

	private enum ApplicationType {

		JVM("JVM", StartJvmApplication.class), NATIVE("native", StartNativeApplication.class);

		private final String description;

		private final Class<? extends StartApplication> startTaskType;

		ApplicationType(String name, Class<? extends StartApplication> startTaskType) {
			this.description = name;
			this.startTaskType = startTaskType;
		}

	}

}
