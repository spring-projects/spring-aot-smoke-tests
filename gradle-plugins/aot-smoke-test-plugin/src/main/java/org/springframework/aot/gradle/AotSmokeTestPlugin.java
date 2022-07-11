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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.avast.gradle.dockercompose.ComposeExtension;
import com.avast.gradle.dockercompose.ComposeSettings;
import io.spring.javaformat.gradle.SpringJavaFormatPlugin;
import org.graalvm.buildtools.gradle.NativeImagePlugin;
import org.graalvm.buildtools.gradle.tasks.BuildNativeImageTask;
import org.gradle.api.JavaVersion;
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
		AotSmokeTestExtension extension = project.getExtensions().create("aotSmokeTest", AotSmokeTestExtension.class,
				project);
		extension.getWebApplication().convention(false);
		JavaPluginExtension javaExtension = project.getExtensions().getByType(JavaPluginExtension.class);
		SourceSet aotTest = javaExtension.getSourceSets().create("aotTest");
		javaExtension.setSourceCompatibility(JavaVersion.VERSION_17);
		javaExtension.setTargetCompatibility(JavaVersion.VERSION_17);
		project.getRepositories().mavenCentral();
		project.getRepositories().maven((repo) -> {
			repo.setName("Spring Milestone");
			repo.setUrl("https://repo.spring.io/milestone");
		});
		project.getRepositories().maven((repo) -> {
			repo.setName("Spring Snapshot");
			repo.setUrl("https://repo.spring.io/snapshot");
		});
		configureJvmTests(project, aotTest, extension);
		configureNativeImageTests(project, aotTest, extension);
		project.getPlugins().withType(SpringJavaFormatPlugin.class,
				(plugin) -> project.getTasks().named("checkFormatAot").configure((task) -> task.setEnabled(false)));
	}

	private void configureJvmTests(Project project, SourceSet aotTest, AotSmokeTestExtension extension) {
		Provider<RegularFile> bootJarArchive = project.getTasks()
				.named(SpringBootPlugin.BOOT_JAR_TASK_NAME, BootJar.class)
				.flatMap((bootJar) -> bootJar.getArchiveFile());
		configureTasks(project, aotTest, ApplicationType.JVM, bootJarArchive, extension);
	}

	private void configureNativeImageTests(Project project, SourceSet aotTest, AotSmokeTestExtension extension) {
		ApplicationType type = ApplicationType.NATIVE;
		project.getPlugins().withType(NativeImagePlugin.class, (nativeImagePlugin) -> {
			Provider<RegularFile> applicationBinary = project.getTasks()
					.named(NativeImagePlugin.NATIVE_COMPILE_TASK_NAME, BuildNativeImageTask.class)
					.flatMap((nativeCompile) -> nativeCompile.getOutputFile());
			configureTasks(project, aotTest, type, applicationBinary, extension);
		});
	}

	private void configureTasks(Project project, SourceSet aotTest, ApplicationType type,
			Provider<RegularFile> nativeImage, AotSmokeTestExtension extension) {
		Provider<Directory> outputDirectory = project.getLayout().getBuildDirectory()
				.dir(type.name().toLowerCase() + "App");
		ComposeSettings composeSettings = composeSettingsForProject(project, type);
		TaskProvider<? extends StartApplication> startTask = createStartApplicationTask(project, type, nativeImage,
				outputDirectory, extension, composeSettings);
		TaskProvider<StopApplication> stopTask = createStopApplicationTask(project, type, startTask, composeSettings);
		createAotTestTask(project, aotTest, type, startTask, stopTask);
	}

	private ComposeSettings composeSettingsForProject(Project project, ApplicationType type) {
		if (!project.file("docker-compose.yml").canRead()) {
			return null;
		}
		project.getPlugins().apply("com.avast.gradle.docker-compose");
		ComposeExtension compose = project.getExtensions().getByType(ComposeExtension.class);
		return compose.nested(type.name().toLowerCase() + "App");
	}

	private String capitalize(String input) {
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}

	private TaskProvider<StopApplication> createStopApplicationTask(Project project, ApplicationType type,
			TaskProvider<? extends StartApplication> startTask, ComposeSettings composeSettings) {
		String taskName = "stop" + capitalize(type.name().toLowerCase()) + "App";
		return project.getTasks().register(taskName, StopApplication.class, (stop) -> {
			stop.getPidFile().set(startTask.flatMap(StartApplication::getPidFile));
			stop.setDescription("Stops the " + type.description + " application.");
			if (composeSettings != null) {
				stop.finalizedBy(composeSettings.getNestedName() + "ComposeDown");
			}
		});
	}

	private TaskProvider<? extends StartApplication> createStartApplicationTask(Project project, ApplicationType type,
			Provider<RegularFile> applicationBinary, Provider<Directory> outputDirectory,
			AotSmokeTestExtension extension, ComposeSettings composeSettings) {
		String taskName = "start" + capitalize(type.name().toLowerCase()) + "App";
		return project.getTasks().register(taskName, type.startTaskType, (start) -> {
			start.getApplicationBinary().set(applicationBinary);
			start.getOutputDirectory().set(outputDirectory);
			start.setDescription("Starts the " + type.description + " application.");
			start.getWebApplication().convention(extension.getWebApplication());
			if (composeSettings != null) {
				start.dependsOn(composeSettings.getNestedName() + "ComposeUp");
				start.environment(project.provider(() -> environment(composeSettings)));
			}
		});
	}

	private Map<String, String> environment(ComposeSettings settings) {
		Map<String, String> environment = new HashMap<>();
		settings.getServicesInfos().forEach((serviceName, service) -> {
			String name = serviceName.toUpperCase(Locale.ENGLISH);
			environment.put(name + "_HOST", service.getHost());
			service.getTcpPorts()
					.forEach((source, target) -> environment.put(name + "_PORT_" + source, Integer.toString(target)));
		});
		return environment;
	}

	private void createAotTestTask(Project project, SourceSet aotTest, ApplicationType type,
			TaskProvider<? extends StartApplication> startTask, TaskProvider<StopApplication> stopTask) {
		String taskName = type.name().toLowerCase() + "AotTest";
		TaskProvider<Test> aotTestTask = project.getTasks().register(taskName, Test.class, (task) -> {
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
		project.getTasks().named(JavaBasePlugin.CHECK_TASK_NAME).configure((check) -> check.dependsOn(aotTestTask));
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
