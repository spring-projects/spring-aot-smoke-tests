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
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Task;
import org.gradle.internal.jvm.Jvm;

/**
 * {@link Task} to start an application on the JVM.
 *
 * @author Andy Wilkinson
 */
public class StartJvmApplication extends StartApplication {

	@Override
	protected ProcessBuilder prepareProcessBuilder(ProcessBuilder processBuilder) {
		File executable = Jvm.current().getJavaExecutable();
		List<String> command = new ArrayList<>();
		command.add(executable.getAbsolutePath());
		command.add("-Dspring.aot.enabled=true");
		if (getWebApplication().get()) {
			command.add("-Dserver.port=0");
		}
		command.add("-jar");
		command.add(getApplicationBinary().get().getAsFile().getAbsolutePath());
		return processBuilder.command(command);
	}

}
