package com.example.webflux.test;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class AutoConfigureWebTestClientTests {

	@Test
	void shouldGetResponse(@Autowired WebTestClient webClient) {
		webClient.get().uri("/hello").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("World");
	}

}
