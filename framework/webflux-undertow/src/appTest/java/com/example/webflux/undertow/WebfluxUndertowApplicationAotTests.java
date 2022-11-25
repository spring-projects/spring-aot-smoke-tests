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

	@Test
	void xmlWorks(WebTestClient client) {
		client.post().uri("/xml").contentType(MediaType.APPLICATION_XML)
				.bodyValue("<request><message>Hello</message></request>").exchange().expectStatus().isOk().expectBody()
				.xml("<response><message>Server: Hello</message></response>");
	}

	@Test
	@Disabled("https://github.com/spring-projects/spring-boot/issues/33347")
	void websocket(@ApplicationUrl(scheme = Scheme.WEBSOCKET) URI applicationUrl) {
		JettyWebSocketClient client = new JettyWebSocketClient();
		client.start();
		try {
			// We can't use StepVerifier here, as it isn't designed to be used in a
			// reactive pipeline
			AtomicReference<List<String>> messages = new AtomicReference<>();
			client.execute(URI.create(applicationUrl.resolve("/ws/count").toString()), session -> session.receive()
					.map(WebSocketMessage::getPayloadAsText).collectList().doOnNext(messages::set).then())
					.block(Duration.ofSeconds(10));
			assertThat(messages.get()).isNotNull().containsExactly("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		finally {
			client.stop();
		}
	}

}
