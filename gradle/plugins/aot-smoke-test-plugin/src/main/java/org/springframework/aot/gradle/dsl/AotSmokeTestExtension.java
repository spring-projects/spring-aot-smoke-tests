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

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

/**
 * DSL extension for configuring AOT smoke tests.
 *
 * @author Andy Wilkinson
 */
public class AotSmokeTestExtension {

	private final Property<Boolean> webApplication;

	private final TestConfiguration appTest;

	private final TestConfiguration nativeAppTest;

	private final TestConfiguration test;

	private final TestConfiguration nativeTest;

	@Inject
	public AotSmokeTestExtension(Project project) {
		ObjectFactory objects = project.getObjects();
		this.webApplication = objects.property(Boolean.class);
		this.appTest = objects.newInstance(TestConfiguration.class, project);
		this.nativeAppTest = objects.newInstance(TestConfiguration.class, project);
		this.test = objects.newInstance(TestConfiguration.class, project);
		this.nativeTest = objects.newInstance(TestConfiguration.class, project);
	}

	/**
	 * Whether the application under test is a web application.
	 * @return whether the application under test is a web application.
	 */
	public Property<Boolean> getWebApplication() {
		return this.webApplication;
	}

	/**
	 * Configuration for {@code appTest}.
	 * @return the configuration
	 */
	public TestConfiguration getAppTest() {
		return this.appTest;
	}

	/**
	 * Configuration for {@code appTest}.
	 * @param action the action to customize the configuration
	 */
	public void appTest(Action<TestConfiguration> action) {
		action.execute(this.appTest);
	}

	/**
	 * Configuration for {@code nativeAppTest}.
	 * @return the configuration
	 */
	public TestConfiguration getNativeAppTest() {
		return this.nativeAppTest;
	}

	/**
	 * Configure {@code nativeAppTest}.
	 * @param action the action to customize the configuration
	 */
	public void nativeAppTest(Action<TestConfiguration> action) {
		action.execute(this.nativeAppTest);
	}

	/**
	 * Configuration for {@code test}.
	 * @return the configuration
	 */
	public TestConfiguration getTest() {
		return this.test;
	}

	/**
	 * Configure {@code test}.
	 * @param action the action to customize the configuration
	 */
	public void test(Action<TestConfiguration> action) {
		action.execute(this.test);
	}

	/**
	 * Configuration for {@code nativeTest}.
	 * @return the configuration
	 */
	public TestConfiguration getNativeTest() {
		return this.nativeTest;
	}

	/**
	 * Configure {@code nativeTest}.
	 * @param action the action to customize the configuration
	 */
	public void nativeTest(Action<TestConfiguration> action) {
		action.execute(this.nativeTest);
	}

	public static class TestConfiguration {

		private final Property<Outcome> outcome;

		@Inject
		public TestConfiguration(Project project) {
			this.outcome = project.getObjects().property(Outcome.class);
			this.outcome.convention(Outcome.SUCCESS);
		}

		/**
		 * The expected outcome.
		 * @return the excepted outcome
		 */
		public Property<Outcome> getOutcome() {
			return this.outcome;
		}

		/**
		 * Note that the expected outcome is failure.
		 * @param action the action to further configure the expectation
		 */
		public void expectedToFail(Action<Object> action) {
			this.outcome.set(Outcome.FAILURE);
		}

	}

	public enum Outcome {

		/**
		 * The expected outcome is failure.
		 */
		FAILURE,

		/**
		 * The expected outcome is success.
		 */
		SUCCESS

	}

}
