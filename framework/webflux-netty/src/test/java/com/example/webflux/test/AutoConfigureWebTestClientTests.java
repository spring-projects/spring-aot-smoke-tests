package com.example.webflux.test;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
class AutoConfigureWebTestClientTests {

	@Test
	void shouldGetResponse(@Autowired WebTestClient webClient) {
		webClient.get().uri("/hello").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("World");
	}

	@Test
	void shouldRunCustomizers() {
		assertThat(Config.CUSTOMIZER_RAN).isTrue();
	}

	@TestConfiguration
	static class Config {

		private static final AtomicBoolean CUSTOMIZER_RAN = new AtomicBoolean(false);

		@Bean
		WebTestClientBuilderCustomizer webTestClientBuilderCustomizer() {
			return (builder) -> CUSTOMIZER_RAN.set(true);
		}

	}
}
