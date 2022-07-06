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

package com.example.webmvc;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class WebMvcApplicationAotTests {

	@Test
	void stringResponseBody(WebTestClient client) {
		client.get().exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.isEqualTo("Hello from Spring MVC and Tomcat"));
	}

	@Test
	void resourceInPublic(WebTestClient client) {
		client.get().uri("bar.html").exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Bar"));
	}

	@Test
	void resourceInStatic(WebTestClient client) {
		client.get().uri("foo.html").exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Foo"));
	}

	@Test
	void jsonResponseFromSerializedRecord(WebTestClient client) {
		client.get().uri("record").exchange().expectStatus().isOk().expectBody()
				.json("{\"message\":\"Hello from Spring MVC and Tomcat\"}");
	}

	@Test
	void sendPostToPostMappingDefinedOnAnInterfaceAndReceiveEchoedJsonResponse(WebTestClient client) {
		client.post().uri("echo").bodyValue("{\"message\": \"Native\"}")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).exchange().expectStatus().isOk()
				.expectBody().json("{\"message\":\"Native\"}");
	}

	@Test
	void customResponseStatusFromResponseStatusAnnotation(WebTestClient client) {
		client.get().uri("status").exchange().expectStatus().isAccepted();
	}

}
