package com.example.webflux.test;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RandomPortTests {

	@LocalServerPort
	int port;

	@Test
	void check() {
		assertThat(this.port).isNotZero();
	}

	@Test
	void webClientWorks(@Autowired WebTestClient webClient) {
		webClient.get().uri("/").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("hi!");
	}

}
