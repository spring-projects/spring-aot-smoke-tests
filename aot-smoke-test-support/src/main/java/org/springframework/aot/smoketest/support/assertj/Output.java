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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.assertj.core.api.AssertProvider;

/**
 * Output from an application.
 *
 * @author Andy Wilkinson
 */
public class Output implements AssertProvider<OutputAssert> {

	private final Path path;

	public Output(Path path) {
		this.path = path;
	}

	public List<String> lines() {
		try {
			return Files.readAllLines(this.path);
		}
		catch (IOException ex) {
			throw new RuntimeException();
		}
	}

	@Override
	public OutputAssert assertThat() {
		return new OutputAssert(this);
	}

}
