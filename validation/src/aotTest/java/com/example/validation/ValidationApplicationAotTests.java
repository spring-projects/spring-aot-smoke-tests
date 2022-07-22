/*
 * Copyright 2022 the original author or authors.
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

package com.example.validation;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class ValidationApplicationAotTests {

	@Test
	void methodValidationWorks(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("this.someService.hello('world'): hello world")
					.hasSingleLineContaining("this.someService.hello(''): Got expected exception").hasNoLinesContaining(
							"this.someService.hello(''): Invocation worked, this should not have happened!");
		});
	}

	@Test
	void configurationPropertiesValidationWorks() {
		// No way to test that without letting the application startup fail
	}

	@Test
	void controllerValidationWorks(WebTestClient client) {
		client.post().uri("/hello").contentType(MediaType.APPLICATION_JSON).bodyValue("{\"name\": \"world\"}")
				.exchange().expectStatus().isOk().expectBody().consumeWith(
						(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Hello world"));
		client.post().uri("/hello").contentType(MediaType.APPLICATION_JSON).bodyValue("{\"name\": \"\"}").exchange()
				.expectStatus().isBadRequest();
	}

}
