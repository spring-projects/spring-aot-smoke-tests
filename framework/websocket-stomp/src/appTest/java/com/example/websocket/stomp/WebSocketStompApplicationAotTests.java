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

package com.example.websocket.stomp;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class WebSocketStompApplicationAotTests {

	@Test
	void clientShouldConnect(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10))
				.untilAsserted(() -> assertThat(output).hasSingleLineContaining("STOMP Client connected"));
	}

	@Test
	void serverShouldSubscribe(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10))
				.untilAsserted(() -> assertThat(output).hasSingleLineContaining("Server: subscription"));
	}

	@Test
	void serverShouldReceiveMessage(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> assertThat(output)
				.hasSingleLineContaining("Server: Received 'HelloMessage{name='STOMP Client'}'"));
	}

	@Test
	void clientShouldReceiveReply(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> assertThat(output)
				.hasSingleLineContaining("Client: Received 'GreetingMessage{content='Hello, STOMP Client!'}'"));
	}

}
