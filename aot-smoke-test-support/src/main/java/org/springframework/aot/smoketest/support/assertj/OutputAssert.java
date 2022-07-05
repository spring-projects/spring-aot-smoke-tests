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
import java.util.stream.Collectors;

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

	public OutputAssert hasSingleLineContaining(String contents) {
		List<String> lines = this.actual.lines();
		List<String> matchingLines = lines.stream().filter((line) -> line.contains(contents))
				.collect(Collectors.toList());
		if (matchingLines.size() != 1) {
			throwAssertionError(
					new BasicErrorMessageFactory("%nExpected %s to have a single line that contains '%s' but found %s",
							lines, contents, matchingLines.size()));
		}
		return this;
	}

	public OutputAssert hasNoLinesContaining(String contents) {
		List<String> lines = this.actual.lines();
		List<String> matchingLines = lines.stream().filter((line) -> line.contains(contents))
				.collect(Collectors.toList());
		if (!matchingLines.isEmpty()) {
			throwAssertionError(
					new BasicErrorMessageFactory("%nExpected %s to have no lines that contain '%s' but found %s", lines,
							contents, matchingLines.size()));
		}
		return this;
	}

}
