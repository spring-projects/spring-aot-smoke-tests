package com.example.session.redis.webflux;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AotSmokeTest
public class SessionRedisWebfluxApplicationTests {

	@Test
	void shouldIncreaseCounter(WebTestClient client) {
		AtomicReference<String> sessionId = new AtomicReference<>();
		client.get().uri("/counter").exchange().expectCookie().value("SESSION", sessionId::set).expectBody()
				.jsonPath("$.counter").isEqualTo(1);
		client.get().uri("/counter").cookie("SESSION", sessionId.get()).exchange().expectBody().jsonPath("$.counter")
				.isEqualTo(2);
		client.get().uri("/counter").cookie("SESSION", sessionId.get()).exchange().expectBody().jsonPath("$.counter")
				.isEqualTo(3);
	}

}
