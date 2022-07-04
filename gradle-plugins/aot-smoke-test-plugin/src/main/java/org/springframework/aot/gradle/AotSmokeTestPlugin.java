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

import org.graalvm.buildtools.gradle.NativeImagePlugin;
import org.graalvm.buildtools.gradle.tasks.BuildNativeImageTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.Directory;
import org.gradle.api.file.RegularFile;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.testing.Test;

import org.springframework.boot.gradle.plugin.SpringBootPlugin;
import org.springframework.boot.gradle.tasks.bundling.BootJar;

/**
 * {@link Plugin} for an AOT smoke test project. Configures an {@code aotTest} source set
 * and tasks for running the contained tests against the application running on the JVM
 * and as a native image.
 *
 * @author Andy Wilkinson
 */
public class AotSmokeTestPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getPlugins().withType(JavaPlugin.class, (javaPlugin) -> project.getPlugins()
				.withType(SpringBootPlugin.class, (bootPlugin) -> configureBootJavaProject(project)));
	}

	private void configureBootJavaProject(Project project) {
		JavaPluginExtension javaExtension = project.getExtensions().getByType(JavaPluginExtension.class);
		SourceSet aotTest = javaExtension.getSourceSets().create("aotTest");
		configureJvmTests(project, aotTest);
		configureNativeImageTests(project, aotTest);
	}

	private void configureJvmTests(Project project, SourceSet aotTest) {
		Provider<RegularFile> bootJarArchive = project.getTasks()
				.named(SpringBootPlugin.BOOT_JAR_TASK_NAME, BootJar.class)
				.flatMap((bootJar) -> bootJar.getArchiveFile());
		configureTasks(project, aotTest, ApplicationType.JVM, bootJarArchive);
	}

	private void configureNativeImageTests(Project project, SourceSet aotTest) {
		ApplicationType type = ApplicationType.NATIVE;
		project.getPlugins().withType(NativeImagePlugin.class, (nativeImagePlugin) -> {
			Provider<RegularFile> applicationBinary = project.getTasks()
					.named(NativeImagePlugin.NATIVE_COMPILE_TASK_NAME, BuildNativeImageTask.class)
					.flatMap((nativeCompile) -> nativeCompile.getOutputFile());
			configureTasks(project, aotTest, type, applicationBinary);
		});
	}

	private void configureTasks(Project project, SourceSet aotTest, ApplicationType type,
			Provider<RegularFile> nativeImage) {
		Provider<Directory> outputDirectory = project.getLayout().getBuildDirectory()
				.dir(type.name().toLowerCase() + "App");
		TaskProvider<? extends StartApplication> startTask = createStartApplicationTask(project,
				"start" + capitalize(type.name().toLowerCase()) + "App", type, nativeImage, outputDirectory);
		TaskProvider<StopApplication> stopTask = createStopApplicationTask(project,
				"stop" + capitalize(type.name().toLowerCase()) + "App", type, startTask);
		createAotTestTask(project, type.name().toLowerCase() + "AotTest", aotTest, type, startTask, stopTask);
	}

	private String capitalize(String input) {
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}

	private TaskProvider<StopApplication> createStopApplicationTask(Project project, String name, ApplicationType type,
			TaskProvider<? extends StartApplication> startTask) {
		return project.getTasks().register(name, StopApplication.class, (stop) -> {
			stop.getPidFile().set(startTask.flatMap(StartApplication::getPidFile));
			stop.setDescription("Stops the " + type.description + " application.");
		});
	}

	private TaskProvider<? extends StartApplication> createStartApplicationTask(Project project, String name,
			ApplicationType type, Provider<RegularFile> applicationBinary, Provider<Directory> outputDirectory) {
		return project.getTasks().register(name, type.startTaskType, (start) -> {
			start.getApplicationBinary().set(applicationBinary);
			start.getOutputDirectory().set(outputDirectory);
			start.setDescription("Starts the " + type.description + " application.");
		});
	}

	private void createAotTestTask(Project project, String taskName, SourceSet aotTest, ApplicationType type,
			TaskProvider<? extends StartApplication> startTask, TaskProvider<StopApplication> stopTask) {
		TaskProvider<Test> nativeAotTest = project.getTasks().register(taskName, Test.class, (task) -> {
			task.dependsOn(startTask);
			task.useJUnitPlatform();
			task.setTestClassesDirs(aotTest.getOutput().getClassesDirs());
			task.setClasspath(aotTest.getRuntimeClasspath());
			task.getInputs().file(startTask.flatMap(StartApplication::getApplicationBinary))
					.withPropertyName("applicationBinary");
			task.systemProperty("org.springframework.aot.smoketest.standard-output",
					startTask.get().getOutputFile().get().getAsFile().getAbsolutePath());
			task.finalizedBy(stopTask);
			task.setDescription("Runs the AOT test suite against the " + type.description + " application.");
			task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
		});
		project.getTasks().named(JavaBasePlugin.CHECK_TASK_NAME).configure((check) -> check.dependsOn(nativeAotTest));
	}

	private enum ApplicationType {

		JVM("JVM", StartJvmApplication.class), NATIVE("native image", StartNativeApplication.class);

		private final String description;

		private final Class<? extends StartApplication> startTaskType;

		ApplicationType(String name, Class<? extends StartApplication> startTaskType) {
			this.description = name;
			this.startTaskType = startTaskType;
		}

	}

}
