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

import java.util.Map;

import org.gradle.api.Task;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.testing.Test;

/**
 * {@link Task} to execute the AOT tests.
 *
 * @author Moritz Halbritter
 */
public class AotTestTask extends Test {

	private final MapProperty<String, String> environment;

	public AotTestTask() {
		this.environment = getProject().getObjects().mapProperty(String.class, String.class);
	}

	/**
	 * Adds the provided {@code environment} to the environment of the application.
	 * @param environment the environment
	 */
	public void environment(Provider<Map<String, String>> environment) {
		this.environment.putAll(environment);
	}

	@Override
	public void executeTests() {
		environment(this.environment.get());
		super.executeTests();
	}

}
