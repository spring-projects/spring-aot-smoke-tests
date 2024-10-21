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

package com.example.tracing.brave.zipkin;

import java.time.Duration;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.aot.smoketest.support.junit.DockerComposeHost;
import org.springframework.aot.smoketest.support.junit.DockerComposePort;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class TracingBraveZipkinApplicationAotTests {

	@Test
	void checkSpanInZipkin(@DockerComposeHost("zipkin") String zipkinHost,
			@DockerComposePort(service = "zipkin", port = 9411) int zipkinPort) {
		WebTestClient client = WebTestClient.bindToServer(new JdkClientHttpConnector())
			.baseUrl("http://%s:%d/".formatted(zipkinHost, zipkinPort))
			.build();
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			client.get()
				.uri("/zipkin/api/v2/traces?limit=1")
				.exchange()
				.expectBody()
				.jsonPath("$[0][0].traceId")
				.isNotEmpty()
				.jsonPath("$[0][0].name")
				.isEqualTo("test-observation")
				.jsonPath("$[0][0].localEndpoint.serviceName")
				.isEqualTo("tracing-brave-zipkin")
				.jsonPath("$[0][0].tags.key-1")
				.isEqualTo("value-1")
				.jsonPath("$[0][0].duration")
				.value((duration) -> {
					assertThat(duration).asInstanceOf(InstanceOfAssertFactories.INTEGER)
						.isGreaterThanOrEqualTo((int) Duration.ofSeconds(2).toMillis());
				});
		});
	}

}
