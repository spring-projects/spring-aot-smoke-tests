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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

/**
 * Base class for a {@link Task} that starts and application.
 *
 * @author Andy WilkinsonO
 */
public abstract class StartApplication extends DefaultTask {
	
	private final MapProperty<String, String> environment;

	private final RegularFileProperty applicationBinary;

	private final Property<Boolean> webApplication;

	private final DirectoryProperty outputDirectory;

	private final Provider<RegularFile> pidFile;

	private final Provider<RegularFile> outputFile;

	private final Provider<RegularFile> errorFile;

	public StartApplication() {
		this.environment = getProject().getObjects().mapProperty(String.class, String.class);
		this.applicationBinary = getProject().getObjects().fileProperty();
		this.webApplication = getProject().getObjects().property(Boolean.class);
		this.outputDirectory = getProject().getObjects().directoryProperty();
		this.pidFile = this.outputDirectory.map((dir) -> dir.file("pid"));
		this.outputFile = this.outputDirectory.map((dir) -> dir.file("output.txt"));
		this.errorFile = this.outputDirectory.map((dir) -> dir.file("error.txt"));
	}

	/**
	 * The application's binary.
	 * @return the application's binary
	 */
	@InputFile
	public RegularFileProperty getApplicationBinary() {
		return this.applicationBinary;
	}

	/**
	 * Whether the application to be started is a web application. When it is,
	 * the application is started with {@code -Dserver.port=0} to prevent port clashes.
	 * @return whether the application is a web application
	 */
	@Input
	public Property<Boolean> getWebApplication() {
		return this.webApplication;
	}

	/**
	 * The directory to which output should be written. Controls the location of the pid,
	 * output, and error files.
	 * @return the output directory
	 */
	@Internal
	public DirectoryProperty getOutputDirectory() {
		return this.outputDirectory;
	}

	/**
	 * The file to which the application's pid should be written.
	 * @return the pid file
	 */
	@Internal
	public Provider<RegularFile> getPidFile() {
		return this.pidFile;
	}

	/**
	 * The file to which the application's redirected output stream should be written.
	 * @return the output file
	 */
	@Internal
	public Provider<RegularFile> getOutputFile() {
		return this.outputFile;
	}

	/**
	 * The file to which the application's redirected error stream should be written.
	 * @return the error file
	 */
	@Internal
	public Provider<RegularFile> getErrorFile() {
		return this.errorFile;
	}
	
	/**
	 * Adds the provided {@code environment} to the environment of the application.
	 * @param environment the environment
	 */
	public void environment(Provider<Map<String, String>> environment) {
		this.environment.putAll(environment);
	}

	@TaskAction
	void startApplication() throws IOException {
		this.outputDirectory.getAsFile().get().mkdirs();
		File redirectedError = this.errorFile.get().getAsFile();
		File redirectedOutput = this.outputFile.get().getAsFile();
		Path pid = this.pidFile.get().getAsFile().toPath();
		Files.deleteIfExists(redirectedError.toPath());
		Files.deleteIfExists(redirectedOutput.toPath());
		Files.deleteIfExists(pid);
		ProcessBuilder processBuilder = prepareProcessBuilder(new ProcessBuilder()).redirectError(redirectedError)
				.redirectOutput(redirectedOutput);
		processBuilder.environment().putAll(this.environment.get());
		Process process = processBuilder.start();
		Files.write(pid, List.of(Long.toString(process.pid())));
	}

	protected abstract ProcessBuilder prepareProcessBuilder(ProcessBuilder processBuilder);

}
