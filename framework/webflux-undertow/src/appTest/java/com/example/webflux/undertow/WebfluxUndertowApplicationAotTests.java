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

package com.example.webflux.undertow;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.aot.smoketest.support.junit.ApplicationUrl;
import org.springframework.aot.smoketest.support.junit.ApplicationUrl.Scheme;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.JettyWebSocketClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class WebfluxUndertowApplicationAotTests {

	@Test
	void stringResponseBody(WebTestClient client) {
		client.get()
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("hi!"));
	}

	@Test
	void anotherStringResponseBody(WebTestClient client) {
		client.get()
			.uri("x")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("hix!"));
	}

	@Test
	void stringMonoResponseBody(WebTestClient client) {
		client.get()
			.uri("hello")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("World"));
	}

	@Test
	void jsonResponseFromSerializedRecordMono(WebTestClient client) {
		client.get()
			.uri("record")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.json("{\"field1\":\"Hello\", \"field2\":\"World\"}");
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
	void coroutines(WebTestClient client) {
		client.post().uri("/coroutine").contentType(MediaType.APPLICATION_JSON).bodyValue("""
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
		client.post()
			.uri("/xml")
			.contentType(MediaType.APPLICATION_XML)
			.bodyValue("<request><message>Hello</message></request>")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.xml("<response><message>Server: Hello</message></response>");
	}

	@Test
	@SuppressWarnings({ "deprecation", "removal" })
	void websocket(@ApplicationUrl(scheme = Scheme.WEBSOCKET) URI applicationUrl) {
		JettyWebSocketClient client = new JettyWebSocketClient();
		client.start();
		try {
			// We can't use StepVerifier here, as it isn't designed to be used in a
			// reactive pipeline
			AtomicReference<List<String>> messages = new AtomicReference<>();
			client
				.execute(URI.create(applicationUrl.resolve("/ws/count").toString()),
						session -> session.receive()
							.map(WebSocketMessage::getPayloadAsText)
							.collectList()
							.doOnNext(messages::set)
							.then())
				.block(Duration.ofSeconds(10));
			assertThat(messages.get()).isNotNull().containsExactly("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		finally {
			client.stop();
		}
	}

}
