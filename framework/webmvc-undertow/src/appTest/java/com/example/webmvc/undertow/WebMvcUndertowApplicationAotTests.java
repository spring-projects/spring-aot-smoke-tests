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

package com.example.webmvc.undertow;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class WebMvcUndertowApplicationAotTests {

	@Test
	void stringResponseBody(WebTestClient client) {
		client.get().exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.isEqualTo("Hello from Spring MVC and Undertow"));
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
				.json("{\"message\":\"Hello from Spring MVC and Undertow\"}");
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

	@Test
	void formSubmission(WebTestClient client) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("firstName", "first-name");
		formData.add("lastName", "last-name");
		client.post().uri("/form-submission").contentType(MediaType.MULTIPART_FORM_DATA)
				.body(BodyInserters.fromMultipartData(formData)).exchange().expectStatus().isOk().expectBody().json("""
						{
							"firstName": "first-name",
							"lastName": "last-name"
						}
						""");
	}

	@Test
	void dataClass(WebTestClient client) {
		client.post().uri("/data-class").contentType(MediaType.APPLICATION_JSON).bodyValue("""
				{
					"greeting": "Hello",
					"name": "Kotlin"
				}
				""").exchange().expectStatus().isOk().expectBody().json("""
				{
					"greeting": "Howdy!",
					"name": "Kotlin"
				}
				""");
	}

	@Test
	void xmlWorks(WebTestClient client) {
		client.post().uri("/xml").contentType(MediaType.APPLICATION_XML)
				.bodyValue("<Request><message>Hello</message></Request>").exchange().expectStatus().isOk().expectBody()
				.xml("<Response><message>Server: Hello</message></Response>");
	}

}
