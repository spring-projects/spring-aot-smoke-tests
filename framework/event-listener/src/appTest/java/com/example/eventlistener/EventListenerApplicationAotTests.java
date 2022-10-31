package com.example.eventlistener;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class EventListenerApplicationAotTests {

	@Test
	void eventIsPublishedAndReceived(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("HelloEventPublisher: Publishing HelloEvent");
			assertThat(output)
					.hasSingleLineContaining("HelloEventListener: Got event HelloEvent{greeting='Hello world'}");
		});
	}

}
