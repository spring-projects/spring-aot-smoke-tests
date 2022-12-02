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

package org.springframework.aot.smoketest.support.assertj;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.error.BasicErrorMessageFactory;

import org.springframework.aot.smoketest.support.Output;

/**
 * An {@link AbstractAssert} for {@link Output}.
 *
 * @author Andy Wilkinson
 */
public class OutputAssert extends AbstractAssert<OutputAssert, Output> {

	OutputAssert(Output actual) {
		super(actual, OutputAssert.class);
	}

	/**
	 * Asserts that the output has a single line matching the given contents.
	 * @param contents contents to match
	 * @return {@code this} for fluent API
	 */
	public OutputAssert hasSingleLineContaining(String contents) {
		List<String> lines = this.actual.lines();
		List<String> matchingLines = lines.stream().filter((line) -> line.contains(contents)).toList();
		if (matchingLines.size() != 1) {
			throwAssertionError(
					new BasicErrorMessageFactory("%nExpected %s to have a single line that contains '%s' but found %s",
							lines, contents, matchingLines.size()));
		}
		return this;
	}

	/**
	 * Asserts that the output has a line matching the given contents.
	 * @param contents contents to match
	 * @return {@code this} for fluent API
	 */
	public OutputAssert hasLineContaining(String contents) {
		List<String> lines = this.actual.lines();
		Optional<String> matchingLines = lines.stream().filter((line) -> line.contains(contents)).findAny();
		if (matchingLines.isEmpty()) {
			throwAssertionError(new BasicErrorMessageFactory(
					"%nExpected %s to have a line that contains '%s' but found none", lines, contents));
		}
		return this;
	}

	/**
	 * Asserts that the output has a line matching the given contents.
	 * @param regex contents to match
	 * @return {@code this} for fluent API
	 */
	public OutputAssert hasLineMatching(String regex) {
		List<String> lines = this.actual.lines();
		Optional<String> matchingLines = lines.stream().filter((line) -> line.matches(regex)).findAny();
		if (matchingLines.isEmpty()) {
			throwAssertionError(new BasicErrorMessageFactory(
					"%nExpected %s to have a line that matches '%s' but found none", lines, regex));
		}
		return this;
	}

	/**
	 * Asserts that the output doesn't have a line matching the given contents.
	 * @param contents contents to match
	 * @return {@code this} for fluent API
	 */
	public OutputAssert hasNoLinesContaining(String contents) {
		List<String> lines = this.actual.lines();
		boolean noMatch = lines.stream().noneMatch((line) -> line.contains(contents));
		if (!noMatch) {
			throwAssertionError(new BasicErrorMessageFactory(
					"%nExpected %s to have no lines that contain '%s' but found some", lines, contents));
		}
		return this;
	}

}
