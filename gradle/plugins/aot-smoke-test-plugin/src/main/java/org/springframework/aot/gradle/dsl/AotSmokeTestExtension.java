/*
 * Copyright 2022-2023 the original author or authors.
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
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Nested;

/**
 * DSL extension for configuring AOT smoke tests.
 *
 * @author Andy Wilkinson
 */
public class AotSmokeTestExtension {

	private final Property<Boolean> webApplication;

	private final SlackNotifications slackNotifications;

	@Inject
	public AotSmokeTestExtension(Project project) {
		this.webApplication = project.getObjects().property(Boolean.class);
		this.slackNotifications = project.getObjects().newInstance(SlackNotifications.class, project);
	}

	/**
	 * Whether the application under test is a web application.
	 * @return whether the application under test is a web application.
	 */
	public Property<Boolean> getWebApplication() {
		return this.webApplication;
	}

	/**
	 * Configures slack notifications for the project's smoke tests.
	 * @param action the action that's called to configure notifications
	 */
	public void slackNotifications(Action<SlackNotifications> action) {
		action.execute(this.slackNotifications);
	}

	/**
	 * Slack notification configuration for the project's smoke tests.
	 * @return the slack notification configuration for the project
	 */
	@Nested
	public SlackNotifications getSlackNotifications() {
		return this.slackNotifications;
	}

	public static class SlackNotifications {

		private Property<String> channel;

		private Property<Boolean> onSuccess;

		private Property<Boolean> onFailure;

		@Inject
		public SlackNotifications(Project project) {
			this.channel = project.getObjects().property(String.class).convention("");
			this.onSuccess = project.getObjects().property(Boolean.class).convention(false);
			this.onFailure = project.getObjects().property(Boolean.class).convention(false);
		}

		/**
		 * Slack channel to which notifications should be published.
		 * @return Slack channel to which notifications should be published.
		 */
		@Input
		public Property<String> getChannel() {
			return this.channel;
		}

		/**
		 * Whether to publish notifications on successful execution of a smoke test.
		 * @return whether to publish notifications on success
		 */
		@Input
		public Property<Boolean> getOnSuccess() {
			return this.onSuccess;
		}

		/**
		 * Whether to publish notifications on failed execution of a smoke test.
		 * @return whether to publish notifications on failure
		 */
		@Input
		public Property<Boolean> getOnFailure() {
			return this.onFailure;
		}

	}

}
