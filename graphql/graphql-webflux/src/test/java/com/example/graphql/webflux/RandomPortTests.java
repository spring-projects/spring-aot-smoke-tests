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

package com.example.graphql.webflux;

import java.net.URI;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
class RandomPortTests {

	@LocalServerPort
	private int localPort;

	@Test
	void getProjectUsingHttp(@Autowired HttpGraphQlTester graphQlTester) {
		graphQlTester.documentName("project")
			.execute()
			.path("project.name")
			.entity(String.class)
			.isEqualTo("Spring Boot");
	}

	@Test
	void getProjectUsingWebSocket() {
		WebSocketClient webClient = new ReactorNettyWebSocketClient();
		URI uri = URI.create("http://localhost:" + localPort + "/graphql");
		WebSocketGraphQlTester graphQlTester = WebSocketGraphQlTester.create(uri, webClient);
		graphQlTester.documentName("project")
			.execute()
			.path("project.name")
			.entity(String.class)
			.isEqualTo("Spring Boot");
	}

}