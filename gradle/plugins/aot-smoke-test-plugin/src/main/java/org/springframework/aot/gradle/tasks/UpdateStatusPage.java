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

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * Task to update an Asciidoctor status page for the smoke tests.
 *
 * @author Andy Wilkinson
 */
public abstract class UpdateStatusPage extends AbstractSmokeTestsTask {

	@OutputFile
	public abstract RegularFileProperty getOutputFile();

	@TaskAction
	void updateStatusPage() throws IOException {
		List<String> lines = new ArrayList<>();
		lines.add("|===");
		smokeTests().forEach((group, tests) -> {
			lines.add("5+^h|" + capitalize(group));
			lines.add("h|Smoke Test");
			for (TestType testType : TestType.values()) {
				lines.add("h|" + testType.taskName());
			}
			for (SmokeTest test : tests) {
				lines.add("|" + test.name());
				for (TestType testType : TestType.values()) {
					lines.add("|" + testType.badge(test));
				}
				lines.add("");
			}
			lines.add("");
		});
		lines.add("|===");
		Files.write(getOutputFile().get().getAsFile().toPath(), lines);
	}

	private String capitalize(String input) {
		StringBuffer buffer = new StringBuffer(input.length());
		for (char c : input.toCharArray()) {
			buffer.append(buffer.isEmpty() ? Character.toUpperCase(c) : c);
		}
		return buffer.toString();
	}

	private enum TestType {

		APP_TEST(SmokeTest::appTests, "-app-test", "appTest"),
		NATIVE_APP_TEST(SmokeTest::appTests, "-native-app-test", "nativeAppTest"),
		TEST(SmokeTest::tests, "-test", "test"), NATIVE_TEST(SmokeTest::tests, "-native-test", "nativeTest");

		private final Predicate<SmokeTest> predicate;

		private final String urlSuffix;

		private final String taskName;

		TestType(Predicate<SmokeTest> predicate, String suffix, String taskName) {
			this.predicate = predicate;
			this.urlSuffix = suffix;
			this.taskName = taskName;
		}

		String badge(SmokeTest smokeTest) {
			if (!this.predicate.test(smokeTest)) {
				return "";
			}
			return "image:" + badgeUrl(smokeTest.name(), this.urlSuffix) + "[link="
					+ jobUrl(smokeTest.name(), this.urlSuffix) + "]";
		}

		String taskName() {
			return this.taskName;
		}

		private String badgeUrl(String name, String suffix) {
			return "https://ci.spring.io/api/v1/teams/spring-aot-smoke-tests/pipelines/spring-aot-smoke-tests-1.0.x/jobs/"
					+ name + suffix + "/badge";
		}

		private String jobUrl(String name, String suffix) {
			return "https://ci.spring.io/teams/spring-aot-smoke-tests/pipelines/spring-aot-smoke-tests-1.0.x/jobs/"
					+ name + suffix;
		}

	}

}
