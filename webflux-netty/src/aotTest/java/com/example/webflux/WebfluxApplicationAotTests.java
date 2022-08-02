package com.example.webflux;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class WebfluxApplicationAotTests {

	@Test
	void stringResponseBody(WebTestClient client) {
		client.get().exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("hi!"));
	}

	@Test
	void anotherStringResponseBody(WebTestClient client) {
		client.get().uri("x").exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("hix!"));
	}

	@Test
	void stringMonoResponseBody(WebTestClient client) {
		client.get().uri("hello").exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("World"));
	}

	@Test
	void jsonResponseFromSerializedRecordMono(WebTestClient client) {
		client.get().uri("record").exchange().expectStatus().isOk().expectBody()
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

}
