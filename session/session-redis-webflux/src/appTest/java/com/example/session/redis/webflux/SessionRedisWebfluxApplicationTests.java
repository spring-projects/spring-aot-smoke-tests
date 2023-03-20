package com.example.session.redis.webflux;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApplicationTest
public class SessionRedisWebfluxApplicationTests {

	@Test
	void shouldIncreaseCounter(WebTestClient client) {
		AtomicReference<String> sessionId = new AtomicReference<>();
		client.get()
			.uri("/counter")
			.exchange()
			.expectStatus()
			.isOk()
			.expectCookie()
			.value("SESSION", sessionId::set)
			.expectBody()
			.jsonPath("$.counter")
			.isEqualTo(1);
		client.get()
			.uri("/counter")
			.cookie("SESSION", sessionId.get())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.counter")
			.isEqualTo(2);
		client.get()
			.uri("/counter")
			.cookie("SESSION", sessionId.get())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.counter")
			.isEqualTo(3);
	}

}
