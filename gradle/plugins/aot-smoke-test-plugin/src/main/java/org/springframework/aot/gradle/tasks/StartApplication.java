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

package org.springframework.aot.gradle.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
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

	public StartApplication() {
		getPidFile().convention(getOutputDirectory().map((dir) -> dir.file("pid")));
		getOutputFile().convention(getOutputDirectory().map((dir) -> dir.file("output.txt")));
		getErrorFile().convention(getOutputDirectory().map((dir) -> dir.file("error.txt")));
	}

	/**
	 * The application's binary.
	 * @return the application's binary
	 */
	@InputFile
	public abstract RegularFileProperty getApplicationBinary();

	/**
	 * Whether the application to be started is a web application. When it is, the
	 * application is started with {@code -Dserver.port=0} to prevent port clashes.
	 * @return whether the application is a web application
	 */
	@Input
	public abstract Property<Boolean> getWebApplication();

	/**
	 * The directory to which output should be written. Controls the location of the pid,
	 * output, and error files.
	 * @return the output directory
	 */
	@Internal
	public abstract DirectoryProperty getOutputDirectory();

	/**
	 * The file to which the application's pid should be written.
	 * @return the pid file
	 */
	@Internal
	public abstract RegularFileProperty getPidFile();

	/**
	 * The file to which the application's redirected output stream should be written.
	 * @return the output file
	 */
	@Internal
	public abstract RegularFileProperty getOutputFile();

	/**
	 * The file to which the application's redirected error stream should be written.
	 * @return the error file
	 */
	@Internal
	public abstract RegularFileProperty getErrorFile();

	@Internal
	public abstract MapProperty<String, String> getInternalEnvironment();

	@TaskAction
	void startApplication() throws IOException {
		getOutputDirectory().getAsFile().get().mkdirs();
		File redirectedError = getErrorFile().get().getAsFile();
		File redirectedOutput = getOutputFile().get().getAsFile();
		Path pid = getPidFile().get().getAsFile().toPath();
		Files.deleteIfExists(redirectedError.toPath());
		Files.deleteIfExists(redirectedOutput.toPath());
		Files.deleteIfExists(pid);
		ProcessBuilder processBuilder = prepareProcessBuilder(new ProcessBuilder()).redirectError(redirectedError)
				.redirectOutput(redirectedOutput);
		processBuilder.environment().putAll(getInternalEnvironment().get());
		Process process = processBuilder.start();
		Files.write(pid, List.of(Long.toString(process.pid())));
	}

	protected abstract ProcessBuilder prepareProcessBuilder(ProcessBuilder processBuilder);

}
