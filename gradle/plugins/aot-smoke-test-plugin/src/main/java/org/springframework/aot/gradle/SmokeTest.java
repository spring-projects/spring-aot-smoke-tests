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
import java.util.List;

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

	/**
	 * An individual test within a smoke test.
	 *
	 * @param taskName the name of the task that runs the test
	 * @param expectedToFail whether to task is expected to fail
	 * @param javaVersion the version of Java that the test requires. May be {@code null}
	 * to use the default version.
	 * @param graalVersion the version of GraalVM that the test requires. May be
	 * {@code null} to use the default version.
	 */
	public record Test(String taskName, boolean expectedToFail, String javaVersion,
			String graalVersion) implements Serializable {

	}

}
