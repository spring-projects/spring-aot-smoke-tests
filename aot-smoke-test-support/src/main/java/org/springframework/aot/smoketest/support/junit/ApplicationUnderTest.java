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

package org.springframework.aot.smoketest.support.junit;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import org.springframework.aot.smoketest.support.Output;

/**
 * Provides access to data about the application which is being tested.
 *
 * @author Moritz Halbritter
 */
final class ApplicationUnderTest {

	private ApplicationUnderTest() {
	}

	private int port = -1;

	/**
	 * Gets the port of the application.
	 * @return the application port
	 */
	int getPort() {
		if (this.port != -1) {
			return this.port;
		}
		List<Pattern> portPatterns = List.of(Pattern.compile("Tomcat started on port ([0-9]+)"),
				Pattern.compile("Netty started on port ([0-9]+)"),
				Pattern.compile("Jetty started on port ([0-9]+)"),
				Pattern.compile("Undertow started on port ([0-9]+)"));
		List<String> lines = Output.current().lines();
		for (String line : lines) {
			for (Pattern portPattern : portPatterns) {
				Matcher matcher = portPattern.matcher(line);
				if (matcher.find()) {
					this.port = Integer.parseInt(matcher.group(1));
					return this.port;
				}
			}
		}
		StringBuilder message = new StringBuilder("Port log message was not found in output:");
		message.append("\n\n");
		if (lines.isEmpty()) {
			message.append("<< none >>");
		}
		else {
			for (String line : lines) {
				message.append(line).append("\n");
			}
		}
		message.append("\n");
		throw new IllegalStateException(message.toString());
	}

	/**
	 * Returns the current {@link ApplicationUnderTest} for the given
	 * {@link ExtensionContext}. Never returns null.
	 * @param extensionContext JUnit extension context
	 * @return current {@link ApplicationUnderTest}
	 */
	static ApplicationUnderTest get(ExtensionContext extensionContext) {
		Store store = extensionContext.getStore(Namespace.create(ApplicationUnderTest.class));
		return (ApplicationUnderTest) store.getOrComputeIfAbsent(ApplicationUnderTest.class,
				(c) -> new ApplicationUnderTest());
	}

}
