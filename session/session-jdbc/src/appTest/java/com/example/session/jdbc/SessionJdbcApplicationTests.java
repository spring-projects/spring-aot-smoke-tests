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

package com.example.session.jdbc;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApplicationTest
public class SessionJdbcApplicationTests {

	@Test
	void shouldIncreaseCounter(WebTestClient client) {
		AtomicReference<String> sessionId = new AtomicReference<>();
		client.get()
			.uri("/counter")
			.exchange()
			.expectCookie()
			.value("SESSIONCOOKIE", sessionId::set)
			.expectBody()
			.jsonPath("$.counter")
			.isEqualTo(1);
		client.get()
			.uri("/counter")
			.cookie("SESSIONCOOKIE", sessionId.get())
			.exchange()
			.expectBody()
			.jsonPath("$.counter")
			.isEqualTo(2);
		client.get()
			.uri("/counter")
			.cookie("SESSIONCOOKIE", sessionId.get())
			.exchange()
			.expectBody()
			.jsonPath("$.counter")
			.isEqualTo(3);
	}

}
