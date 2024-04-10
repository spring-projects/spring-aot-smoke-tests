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

package org.springframework.aot.gradle.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import org.springframework.aot.gradle.SmokeTest;

/**
 * Task to describe a smoke test in a consumable format.
 *
 * @author Andy Wilkinson
 */
public abstract class DescribeSmokeTest extends DefaultTask {

	public DescribeSmokeTest() {
		getOutputFile().convention(getProject().getLayout()
			.getBuildDirectory()
			.file(getSmokeTest()
				.map((smokeTest) -> "smoke-tests/" + smokeTest.group() + "-" + smokeTest.name() + ".properties")));
	}

	@OutputFile
	public abstract RegularFileProperty getOutputFile();

	@Input
	public abstract Property<SmokeTest> getSmokeTest();

	@TaskAction
	void describeSmokeTest() throws IOException {
		SmokeTest smokeTest = getSmokeTest().get();
		File propertiesFile = getOutputFile().getAsFile().get();
		List<String> properties = new ArrayList<>();
		properties.add("appTests=" + smokeTest.appTests());
		properties.add("group=" + smokeTest.group());
		properties.add("name=" + smokeTest.name());
		properties.add("path=" + smokeTest.path());
		properties.add("tests=" + smokeTest.tests());
		properties.add("expectedToFail=" + String.join(",", smokeTest.expectedToFail()));
		Files.write(propertiesFile.toPath(), properties);
	}

}
