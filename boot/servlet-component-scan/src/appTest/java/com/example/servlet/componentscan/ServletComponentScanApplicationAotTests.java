package com.example.servlet.componentscan;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class ServletComponentScanApplicationAotTests {

	@Test
	void componentsAreRegistered(WebTestClient client, AssertableOutput output) {
		client.get()
			.uri("/")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
				.isEqualTo("Hello from Servlet and hello from filter, too!"));
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("TestListener.requestInitialized");
			assertThat(output).hasSingleLineContaining("TestListener.requestDestroyed");
		});
	}

}
