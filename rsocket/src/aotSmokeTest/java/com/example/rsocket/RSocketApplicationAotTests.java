package com.example.rsocket;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class RSocketApplicationAotTests {

	@Test
	void messageIsReceivedAndAnswered(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("Server: message(): Message{origin='client', message='Hello!'}")
					.hasSingleLineContaining("Client: message(): Message{origin='server', message='Hello!'}");
		});
	}

	@Test
	void reactiveMessageIsReceivedAndAnswered(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("Server: reactiveMessage: Message{origin='client', message='Hello!'}")
					.hasSingleLineContaining("Client: reactiveMessage(): Message{origin='server', message='Hello!'}");
		});
	}

	@Test
	void messageRecordIsReceivedAndAnswered(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("Server: messageRecord(): MessageRecord[origin=client, message=Hello!]")
					.hasSingleLineContaining("Client: messageRecord(): MessageRecord[origin=server, message=Hello!]");
		});
	}

}
