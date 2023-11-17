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

package org.springframework.aot.gradle.tasks;

import java.util.Properties;

/**
 * A smoke test.
 *
 * @author Andy Wilkinson
 * @param name name of the smoke test
 * @param group group of the smoke test
 * @param path path of the smoke test project
 * @param tests whether the smoke test contains any unit tests
 * @param appTests whether the smoke test contains any app tests
 * @param slackNotifications configuration for Slack notifications
 */
record SmokeTest(String name, String group, String path, boolean tests, boolean appTests,
		SmokeTest.SlackNotifications slackNotifications) {

	SmokeTest(Properties properties) {
		this(properties.getProperty("name"), properties.getProperty("group"), properties.getProperty("path"),
				Boolean.valueOf(properties.getProperty("tests")), Boolean.valueOf(properties.getProperty("appTests")),
				SmokeTest.SlackNotifications.from(properties));
	}

	record SlackNotifications(String channel, boolean onSuccess, boolean onFailure) {

		private static SlackNotifications from(Properties properties) {
			String channel = properties.getProperty("slack.channel");
			if (channel == null || channel.isBlank()) {
				return null;
			}
			return new SlackNotifications(channel, Boolean.valueOf(properties.getProperty("slack.on-success")),
					Boolean.valueOf(properties.getProperty("slack.on-failure")));
		}
	}

}
