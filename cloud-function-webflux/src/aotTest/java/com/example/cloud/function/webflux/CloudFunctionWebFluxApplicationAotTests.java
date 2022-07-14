package com.example.cloud.function.webflux;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class CloudFunctionWebFluxApplicationAotTests {

	@Test
	void uppercaseShouldBeInvokable(WebTestClient webTestClient) {
		webTestClient.post().uri("/uppercase").header("Content-Type", MediaType.TEXT_PLAIN_VALUE).bodyValue("hello")
				.exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("HELLO"));
	}

	@Test
	void lowercaseShouldBeInvokable(WebTestClient webTestClient) {
		webTestClient.post().uri("/lowercase").header("Content-Type", MediaType.TEXT_PLAIN_VALUE).bodyValue("HELLO")
				.exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("hello"));
	}

}
