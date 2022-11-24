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

package org.springframework.aot.smoketest.support.junit;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.aot.smoketest.support.Output;

/**
 * {@link BeforeAllCallback} that waits for the application to have started.
 *
 * @author Andy Wilkinson
 */
class AwaitApplication implements BeforeAllCallback {

	private static final Duration START_TIMEOUT = Duration.ofSeconds(30);

	private Pattern APPLICATION_STARTED = Pattern
			.compile("Started [A-Za-z0-9]+ in [0-9\\.]+ seconds \\(process running for [0-9\\.]+\\)");

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		Output output = Output.current();
		long end = Instant.now().plus(START_TIMEOUT).toEpochMilli();
		List<String> lines = null;
		while (System.currentTimeMillis() < end) {
			lines = output.lines();
			for (String line : lines) {
				if (this.APPLICATION_STARTED.matcher(line).find()) {
					return;
				}
			}
		}
		StringBuilder message = new StringBuilder(
				"Started log message was not detected within " + START_TIMEOUT + " in output:");
		message.append("\n\n");
		if (lines == null || lines.isEmpty()) {
			message.append("<< none >>");
		}
		else {
			for (String line : lines) {
				message.append(line + "\n");
			}
		}
		message.append("\n");
		throw new IllegalStateException(message.toString());
	}

}
