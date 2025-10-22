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

package org.springframework.aot.gradle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * A smoke test.
 *
 * @author Andy Wilkinson
 * @param name name of the smoke test
 * @param group group of the smoke test
 * @param path path of the smoke test project
 * @param tests the smoke test's individual tests
 */
public record SmokeTest(String name, String group, String path, List<SmokeTest.Test> tests) implements Serializable {

	public static SmokeTest from(Properties properties) {
		return new SmokeTest(properties.getProperty("name"), properties.getProperty("group"),
				properties.getProperty("path"), testsFrom(properties));
	}

	private static List<SmokeTest.Test> testsFrom(Properties properties) {
		List<SmokeTest.Test> tests = new ArrayList<>();
		testFrom(properties, "appTest", tests::add);
		testFrom(properties, "nativeAppTest", tests::add);
		testFrom(properties, "test", tests::add);
		testFrom(properties, "nativeTest", tests::add);
		return tests;
	}

	private static void testFrom(Properties properties, String name, Consumer<SmokeTest.Test> consumer) {
		if (configuresTest(properties, name)) {
			boolean expectedToFail = Boolean.parseBoolean(properties.getProperty("tests.%s.expectedToFail".formatted(name)));
			String javaVersion = properties.getProperty("tests.%s.javaVersion".formatted(name));
            String graalVersion = properties.getProperty("tests.%s.graalVersion".formatted(name));
			consumer.accept(new SmokeTest.Test(name, expectedToFail, javaVersion, graalVersion));
		}
	}

	private static boolean configuresTest(Properties properties, String name) {
		for (Object key : properties.keySet()) {
			if (key instanceof String string && string.startsWith("tests." + name + ".")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * An individual test within a smoke test.
	 *
	 * @param taskName the name of the task that runs the test
	 * @param expectedToFail whether to task is expected to fail
	 * @param javaVersion the required Java version for the test. May be {@code null} to
	 * use the default version
	 * @param graalVersion the required GraalVM version for the test. May be {@code null}
	 * to use the default version
	 */
	public record Test(String taskName, boolean expectedToFail, String javaVersion, String graalVersion) implements Serializable {

	}

}
