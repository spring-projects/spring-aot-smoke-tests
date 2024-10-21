/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.actuator.webmvc;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class ActuatorWebMvcApplicationAotTests {

	@Test
	void shouldContainLinks(WebTestClient client) {
		client.get()
			.uri("/actuator")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$._links.self.templated")
			.isEqualTo(false)
			.jsonPath("$._links.env-toMatch.templated")
			.isEqualTo(true);
	}

	@Test
	void shouldHaveCustomHealthIndicator(WebTestClient client) {
		client.get()
			.uri("/actuator/health")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.status")
			.isEqualTo("UP")
			.jsonPath("$.components.custom.status")
			.isEqualTo("UP")
			.jsonPath("$.components.custom.details.hello")
			.isEqualTo("world");
	}

	@Test
	void shouldHaveAnotherCustomHealthIndicator(WebTestClient client) {
		client.get()
			.uri("/actuator/health")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.components.anotherCustom.status")
			.isEqualTo("UP");
	}

	@Test
	void shouldHaveCompositeHealth(WebTestClient client) {
		client.get()
			.uri("/actuator/health")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.components.composite.status")
			.isEqualTo("UP")
			.jsonPath("$.components.composite.components.another-custom.status")
			.isEqualTo("UP")
			.jsonPath("$.components.composite.components.custom.status")
			.isEqualTo("UP");
	}

	@Test
	void shouldHaveCustomEndpoint(WebTestClient client) {
		client.get()
			.uri("/actuator/custom")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("custom-read"));
	}

	@Test
	void shouldHaveCustomWebEndpoint(WebTestClient client) {
		client.get()
			.uri("/actuator/customWeb")
			.exchange()
			.expectStatus()
			.isEqualTo(299)
			.expectBody()
			.consumeWith(
					(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("customWeb-read"));
	}

	@Test
	void shouldHaveReadiness(WebTestClient client) {
		client.get()
			.uri("/actuator/health/readiness")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.status")
			.isEqualTo("UP");
	}

	@Test
	void shouldHaveEnvInfoProperties(WebTestClient client) {
		client.get()
			.uri("/actuator/info")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.app.hello")
			.isEqualTo("world");
	}

	@Test
	void shouldHaveJavaInfoProperties(WebTestClient client) {
		client.get()
			.uri("/actuator/info")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.java.version")
			.isNotEmpty();
	}

	@Test
	void shouldHaveOsInfoProperties(WebTestClient client) {
		client.get()
			.uri("/actuator/info")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.os.name")
			.isNotEmpty();
	}

	@Test
	void shouldHaveMetrics(WebTestClient client) {
		client.get()
			.uri("/actuator/metrics/jvm.classes.loaded")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.measurements.[0].value")
			.isNotEmpty();
	}

	@Test
	void prometheusWorks(WebTestClient client) {
		client.get()
			.uri("/actuator/prometheus")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
				// Check custom timer
				.contains("custom_timer_seconds_max 5.0")
				.contains("custom_timer_seconds_count 1.0")
				.contains("custom_timer_seconds_sum 5.0")
				// Check JVM metric
				.contains("# TYPE jvm_threads_peak_threads gauge"));
	}

	@Test
	void shouldHaveLoggers(WebTestClient client) {
		client.get()
			.uri("/actuator/loggers")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.levels")
			.isNotEmpty()
			.jsonPath("$.loggers.['ROOT']")
			.isNotEmpty()
			.jsonPath("$.loggers.['_org.springframework']")
			.isNotEmpty();
	}

}
