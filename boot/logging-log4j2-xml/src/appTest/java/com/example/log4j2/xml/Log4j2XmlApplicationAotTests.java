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

package com.example.log4j2.xml;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class Log4j2XmlApplicationAotTests {

	@Test
	void expectedLoggingIsProduced(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasNoLinesContaining("Trace message")
				.hasSingleLineContaining("main | DEBUG | com.example.log4j2.xml.CLR | Debug message")
				.hasSingleLineContaining("main | INFO  | com.example.log4j2.xml.CLR | Info message")
				.hasSingleLineContaining("main | WARN  | com.example.log4j2.xml.CLR | Warn message")
				.hasSingleLineContaining("main | ERROR | com.example.log4j2.xml.CLR | Error message")
				.hasSingleLineContaining("main | INFO  | com.example.log4j2.xml.CLR | Info with parameters: 1")
				.hasSingleLineContaining("main | ERROR | com.example.log4j2.xml.CLR | Error with stacktrace")
				.hasSingleLineContaining("java.lang.RuntimeException: Boom")
				.hasSingleLineContaining("at com.example.log4j2.xml.CLR.run(CLR.java");
		});
	}

}
