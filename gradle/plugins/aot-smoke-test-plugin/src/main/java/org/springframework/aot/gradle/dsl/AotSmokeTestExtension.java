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

	private final Expectation appTest;

	private final Expectation nativeAppTest;

	private final Expectation test;

	private final Expectation nativeTest;

	@Inject
	public AotSmokeTestExtension(Project project) {
		ObjectFactory objects = project.getObjects();
		this.webApplication = objects.property(Boolean.class);
		this.appTest = objects.newInstance(Expectation.class, project);
		this.nativeAppTest = objects.newInstance(Expectation.class, project);
		this.test = objects.newInstance(Expectation.class, project);
		this.nativeTest = objects.newInstance(Expectation.class, project);
	}

	/**
	 * Whether the application under test is a web application.
	 * @return whether the application under test is a web application.
	 */
	public Property<Boolean> getWebApplication() {
		return this.webApplication;
	}

	/**
	 * Expectations for {@code appTest}.
	 */
	public Expectation getAppTest() {
		return this.appTest;
	}

	/**
	 * Configure expectations for {@code appTest}.
	 */
	public void appTest(Action<Expectation> action) {
		action.execute(this.appTest);
	}

	/**
	 * Expectations for {@code nativeAppTest}.
	 */
	public Expectation getNativeAppTest() {
		return this.nativeAppTest;
	}

	/**
	 * Configure expectations for {@code nativeAppTest}.
	 */
	public void nativeAppTest(Action<Expectation> action) {
		action.execute(this.nativeAppTest);
	}

	/**
	 * Expectations for {@code test}.
	 */
	public Expectation getTest() {
		return this.test;
	}

	/**
	 * Configure expectations for {@code test}.
	 */
	public void test(Action<Expectation> action) {
		action.execute(this.test);
	}

	/**
	 * Expectations for {@code nativeTest}.
	 */
	public Expectation getNativeTest() {
		return this.nativeTest;
	}

	/**
	 * Configure expectations for {@code nativeTest}.
	 */
	public void nativeTest(Action<Expectation> action) {
		action.execute(this.nativeTest);
	}

	public static class Expectation {

		private final Property<Outcome> outcome;

		@Inject
		public Expectation(Project project) {
			this.outcome = project.getObjects().property(Outcome.class);
			this.outcome.convention(Outcome.SUCCESS);
		}

		/**
		 * The expected outcome.
		 */
		public Property<Outcome> getOutcome() {
			return this.outcome;
		}

		/**
		 * Note that expected outcome is failure
		 */
		public void expectedToFail(Action<Object> action) {
			this.outcome.set(Outcome.FAILURE);
		}

	}

	public static enum Outcome {

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
