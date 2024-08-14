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

package com.example.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApplicationTest
public class IntegrationApplicationTests {

	@Test
	void shouldOutputIntegrationGraph(WebTestClient client, AssertableOutput output) {
		client.post().uri("/control-bus/dateSourceEndpoint.start").exchange().expectStatus().isOk();

		output.assertThat().hasSingleLineContaining("started bean 'dateSourceEndpoint'");

		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.untilAsserted(() -> output.assertThat().hasLineContaining("Current seconds:"));

		client.get()
			.uri("/integration-graph")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(String.class)
			.value(graph -> assertThat(graph).contains("null-channel")
				.contains("loggingChannel")
				.contains("dateSourceEndpoint"));
	}

}
