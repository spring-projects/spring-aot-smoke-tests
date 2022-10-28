package com.example.session.jdbc;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApplicationTest
public class SessionJdbcApplicationTests {

	@Test
	void shouldIncreaseCounter(WebTestClient client) {
		AtomicReference<String> sessionId = new AtomicReference<>();
		client.get().uri("/counter").exchange().expectCookie().value("SESSIONCOOKIE", sessionId::set).expectBody()
				.jsonPath("$.counter").isEqualTo(1);
		client.get().uri("/counter").cookie("SESSIONCOOKIE", sessionId.get()).exchange().expectBody()
				.jsonPath("$.counter").isEqualTo(2);
		client.get().uri("/counter").cookie("SESSIONCOOKIE", sessionId.get()).exchange().expectBody()
				.jsonPath("$.counter").isEqualTo(3);
	}

}
