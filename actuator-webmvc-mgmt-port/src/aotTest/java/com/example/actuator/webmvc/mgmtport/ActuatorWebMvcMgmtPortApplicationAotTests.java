package com.example.actuator.webmvc.mgmtport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.Output;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class ActuatorWebMvcMgmtPortApplicationAotTests {

	private static final Pattern MANAGEMENT_PORT_REGEX = Pattern.compile("^Management port: (\\d+)$");

	private static WebTestClient client;

	@BeforeAll
	static void beforeAll() {
		client = buildManagementClient();
	}

	@Test
	void shouldContainLinks() {
		client.get().uri("/actuator").exchange().expectStatus().isOk().expectBody().jsonPath("$._links.self.templated")
				.isEqualTo(false).jsonPath("$._links.env-toMatch.templated").isEqualTo(true);
	}

	@Test
	void shouldHaveReadiness() {
		client.get().uri("/actuator/health/readiness").exchange().expectStatus().isOk().expectBody()
				.jsonPath("$.status").isEqualTo("UP");
	}

	@Test
	void shouldHaveEnvInfoProperties() {
		client.get().uri("/actuator/info").exchange().expectStatus().isOk().expectBody().jsonPath("$.app.hello")
				.isEqualTo("world");
	}

	@Test
	void shouldHaveJavaInfoProperties() {
		client.get().uri("/actuator/info").exchange().expectStatus().isOk().expectBody().jsonPath("$.java.version")
				.isNotEmpty();
	}

	@Test
	void shouldHaveOsInfoProperties() {
		client.get().uri("/actuator/info").exchange().expectStatus().isOk().expectBody().jsonPath("$.os.name")
				.isNotEmpty();
	}

	@Test
	void shouldHaveMetrics() {
		client.get().uri("/actuator/metrics/jvm.classes.loaded").exchange().expectStatus().isOk().expectBody()
				.jsonPath("$.measurements.[0].value").isNotEmpty();
	}

	@Test
	void shouldHavePrometheusMetrics() {
		client.get().uri("/actuator/prometheus").exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.contains("jvm_classes_loaded_classes "));

	}

	private static WebTestClient buildManagementClient() {
		for (String line : Output.current().lines()) {
			Matcher matcher = MANAGEMENT_PORT_REGEX.matcher(line);
			if (matcher.matches()) {
				int port = Integer.parseInt(matcher.group(1));
				return WebTestClient.bindToServer().baseUrl("http://localhost:%d/".formatted(port)).build();
			}
		}
		throw new IllegalStateException("No management port found in output");
	}

}
