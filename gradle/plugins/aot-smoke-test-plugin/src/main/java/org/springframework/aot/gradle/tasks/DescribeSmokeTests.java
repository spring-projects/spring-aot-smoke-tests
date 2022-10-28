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
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/**
 * {@link Task} that describes a project's smoke tests.
 *
 * @author Andy Wilkinson
 */
public abstract class DescribeSmokeTests extends DefaultTask {

	private FileCollection appTests;

	private FileCollection tests;

	public DescribeSmokeTests() {
		getSmokeTestsDescription().put("appTests",
				getProject().provider(() -> Boolean.toString(!this.appTests.isEmpty())));
		getSmokeTestsDescription().put("tests", getProject().provider(() -> Boolean.toString(!this.tests.isEmpty())));
		getSmokeTestsDescription().put("path", getProject().provider(getProject()::getPath));
		getSmokeTestsDescription().put("group", getProject().provider(getProject().getParent()::getName));
		getSmokeTestsDescription().put("name", getProject().provider(getProject()::getName));
	}

	@Input
	abstract MapProperty<String, String> getSmokeTestsDescription();

	@OutputDirectory
	public abstract DirectoryProperty getOutputDirectory();

	@InputFiles
	public FileCollection getAppTests() {
		return this.appTests;
	}

	public void setAppTests(FileCollection appTests) {
		this.appTests = appTests;
	}

	@InputFiles
	public FileCollection getTests() {
		return this.tests;
	}

	public void setTests(FileCollection tests) {
		this.tests = tests;
	}

	@TaskAction
	void describeSmokeTests() throws IOException {
		File smokeTests = getOutputDirectory().file("smoke-tests.properties").get().getAsFile();
		List<String> lines = getSmokeTestsDescription().get().entrySet().stream()
				.map((entry) -> entry.getKey() + "=" + entry.getValue()).sorted().toList();
		Files.write(smokeTests.toPath(), lines);
	}

}
