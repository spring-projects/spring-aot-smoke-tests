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

package org.springframework.aot.gradle.dsl;

import javax.inject.Inject;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

/**
 * DSL extension for configuring AOT smoke tests.
 *
 * @author Andy Wilkinson
 */
public class AotSmokeTestExtension {

	private final Property<Boolean> webApplication;

	@Inject
	public AotSmokeTestExtension(Project project) {
		this.webApplication = project.getObjects().property(Boolean.class);
	}

	/**
	 * Whether the application under test is a web application.
	 * @return whether the application under test is a web application.
	 */
	public Property<Boolean> getWebApplication() {
		return this.webApplication;
	}

}
