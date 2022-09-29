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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.aot.smoketest.support.junit.ApplicationUrl;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

@ApplicationTest
class GraphQlApplicationAotTests {

	@Test
	void getProjectUsingHttp(@ApplicationUrl(scheme = ApplicationUrl.Scheme.HTTP) URI applicationUrl) {
		WebClient webClient = WebClient.create(applicationUrl.toString() + "/graphql");
		HttpGraphQlClient graphQlClient = HttpGraphQlClient.create(webClient);
		Mono<String> projectName = graphQlClient.documentName("project").retrieve("project.name")
				.toEntity(String.class);
		StepVerifier.create(projectName).expectNext("Spring Framework").expectComplete().verify();
	}

	@Test
	void getProjectUsingWebSocket(@ApplicationUrl(scheme = ApplicationUrl.Scheme.WEBSOCKET) URI applicationUrl) {
		WebSocketClient webClient = new ReactorNettyWebSocketClient();
		WebSocketGraphQlClient graphQlClient = WebSocketGraphQlClient
				.builder(applicationUrl.toString() + "/graphql", webClient).build();
		Mono<String> projectName = graphQlClient.documentName("project").retrieve("project.name")
				.toEntity(String.class);
		StepVerifier.create(projectName).expectNext("Spring Framework").expectComplete().verify();
	}

}
