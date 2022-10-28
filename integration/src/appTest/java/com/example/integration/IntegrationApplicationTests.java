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
		client.get().uri("/control-bus/dateSourceEndpoint").exchange().expectStatus().isOk();

		output.assertThat().hasSingleLineContaining("Starting endpoint: dateSourceEndpoint");

		Awaitility.await().atMost(Duration.ofSeconds(30))
				.untilAsserted(() -> output.assertThat().hasLineContaining("Current seconds:"));

		client.get().uri("/integration-graph").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectBody(String.class).value(graph -> assertThat(graph).contains("null-channel")
						.contains("loggingChannel").contains("dateSourceEndpoint"));
	}

}
