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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * {@link Task} to stop a {@link StartApplication} previously started application.
 *
 * @author Andy Wilkinson
 */
public class StopApplication extends DefaultTask {

	private final RegularFileProperty pidFile;

	public StopApplication() {
		this.pidFile = getProject().getObjects().fileProperty();
	}

	/**
	 * The file to which the application's pid has been written.
	 * @return the pid file
	 */
	@InputFile
	public RegularFileProperty getPidFile() {
		return this.pidFile;
	}

	@TaskAction
	void stopJvmApp() throws IOException {
		Path pidPath = this.pidFile.get().getAsFile().toPath();
		List<String> lines = Files.readAllLines(pidPath);
		if (lines.size() == 1) {
			long pid = Long.parseLong(lines.get(0));
			ProcessHandle.of(pid).ifPresent((process) -> process.destroy());
			Files.delete(pidPath);
		}
		else {
			System.out.println("No PID. Assuming process is stopped");
		}
	}

}
