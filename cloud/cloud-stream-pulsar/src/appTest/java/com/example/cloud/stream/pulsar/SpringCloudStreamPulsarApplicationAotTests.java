package com.example.cloud.stream.pulsar;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class SpringCloudStreamPulsarApplicationAotTests {

	@Test
	void suppliedMessageIsUppercasedAndLogged(AssertableOutput output) {
		// INPUT -> How much wood could a woodchuck chuck if a woodchuck could chuck
		// wood?"
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.untilAsserted(() -> assertThat(output).hasLineContaining("++++++Received:HOW")
				.hasLineContaining("++++++Received:MUCH")
				.hasLineContaining("++++++Received:WOOD")
				.hasLineContaining("++++++Received:COULD")
				.hasLineContaining("++++++Received:A")
				.hasLineContaining("++++++Received:WOODCHUCK")
				.hasLineContaining("++++++Received:CHUCK")
				.hasLineContaining("++++++Received:IF")
				.hasLineContaining("++++++Received:COULD")
				.hasLineContaining("++++++Received:WOOD?"));
	}

}
