package com.example.pulsar;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class SpringPulsarReactiveApplicationAotTests {

	@Test
	void reactivePulsarListenerMethodReceivesMessage(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.untilAsserted(() -> assertThat(output).hasSingleLineContaining("Message Received: sample-message-50"));
	}

}
