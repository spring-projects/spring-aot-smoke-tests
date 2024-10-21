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

package com.example.rsocket;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class RSocketApplicationAotTests {

	@Test
	void messageIsReceivedAndAnswered(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("Server: message(): Message{origin='client', message='Hello!'}")
				.hasSingleLineContaining("Client: message(): Message{origin='server', message='Hello!'}");
		});
	}

	@Test
	void reactiveMessageIsReceivedAndAnswered(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining("Server: reactiveMessage: Message{origin='client', message='Hello!'}")
				.hasSingleLineContaining("Client: reactiveMessage(): Message{origin='server', message='Hello!'}");
		});
	}

	@Test
	void messageRecordIsReceivedAndAnswered(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining("Server: messageRecord(): MessageRecord[origin=client, message=Hello!]")
				.hasSingleLineContaining("Client: messageRecord(): MessageRecord[origin=server, message=Hello!]");
		});
	}

	@Test
	void messageExceptionHandler(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("Client: messageExceptionHandler()")
				.hasSingleLineContaining("Server: handleIllegalStateException()");
		});
	}

}
