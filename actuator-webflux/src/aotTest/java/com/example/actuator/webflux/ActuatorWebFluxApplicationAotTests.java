package com.example.actuator.webflux;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AotSmokeTest
class ActuatorWebFluxApplicationAotTests {

	@Test
	void shouldContainLinks(WebTestClient client) {
		client.get().uri("/actuator").exchange().expectStatus().isOk().expectBody().jsonPath("$._links.self.templated")
				.isEqualTo(false).jsonPath("$._links.env-toMatch.templated").isEqualTo(true);
	}

	@Test
	void shouldHaveCustomHealthIndicator(WebTestClient client) {
		client.get().uri("/actuator/health").exchange().expectStatus().isOk().expectBody().jsonPath("$.status")
				.isEqualTo("UP").jsonPath("$.components.custom.status").isEqualTo("UP")
				.jsonPath("$.components.custom.details.hello").isEqualTo("world");
	}

	@Test
	void shouldHaveReadiness(WebTestClient client) {
		client.get().uri("/actuator/health/readiness").exchange().expectStatus().isOk().expectBody()
				.jsonPath("$.status").isEqualTo("UP");
	}

	@Test
	void shouldHaveEnvInfoProperties(WebTestClient client) {
		client.get().uri("/actuator/info").exchange().expectStatus().isOk().expectBody().jsonPath("$.app.hello")
				.isEqualTo("world");
	}

	@Test
	void shouldHaveJavaInfoProperties(WebTestClient client) {
		client.get().uri("/actuator/info").exchange().expectStatus().isOk().expectBody().jsonPath("$.java.version")
				.isNotEmpty();
	}

	@Test
	void shouldHaveOsInfoProperties(WebTestClient client) {
		client.get().uri("/actuator/info").exchange().expectStatus().isOk().expectBody().jsonPath("$.os.name")
				.isNotEmpty();
	}

}
