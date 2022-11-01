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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;

/**
 * Base class for tasks that work with smoke tests.
 *
 * @author Andy Wilkinson
 */
abstract class AbstractSmokeTestsTask extends DefaultTask {

	private FileCollection smokeTestDescriptions;

	@InputFiles
	public FileCollection getSmokeTests() {
		return this.smokeTestDescriptions;
	}

	public void setSmokeTests(FileCollection smokeTests) {
		this.smokeTestDescriptions = smokeTests;
	}

	Map<String, SortedSet<SmokeTest>> smokeTests() {
		List<SmokeTest> smokeTests = this.smokeTestDescriptions.getFiles().stream().map(this::readProperties)
				.map(SmokeTest::new).collect(Collectors.toList());
		Map<String, SortedSet<SmokeTest>> groupedSmokeTests = new TreeMap<>();
		for (SmokeTest smokeTest : smokeTests) {
			groupedSmokeTests.computeIfAbsent(smokeTest.group(),
					(group) -> new TreeSet<>((one, two) -> one.name().compareTo(two.name()))).add(smokeTest);
		}
		return groupedSmokeTests;
	}

	private Properties readProperties(File smokeTest) {
		Properties properties = new Properties();
		try (InputStream input = new FileInputStream(new File(smokeTest, "smoke-tests.properties"))) {
			properties.load(input);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return properties;
	}

}
