package com.example.cloud.config.server;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Olga Maciaszek-Sharma
 */
@ApplicationTest
public class ConfigServerApplicationAotTests {

	@Test
	void shouldRetrieveConfig(AssertableOutput output, WebTestClient client) {
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(output).hasLineContaining("Starting AOT-processed ConfigServerApplication");
		});
		client.get()
			.uri("/client-service/default")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(String.class)
			.consumeWith(body -> assertThat(body.getResponseBody()).contains("\"simple.test-x\":\"testN\""));
	}

}
