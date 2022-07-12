package com.example.scheduled;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class ScheduledApplicationAotTests {

	@Test
	void fixedRateShouldBeCalled(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10))
				.untilAsserted(() -> assertThat(output).hasLineContaining("fixedRate()"));
	}

	@Test
	void fixedDelayShouldBeCalled(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10))
				.untilAsserted(() -> assertThat(output).hasLineContaining("fixedDelay()"));
	}

	@Test
	void cronShouldBeCalled(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10))
				.untilAsserted(() -> assertThat(output).hasLineContaining("cron()"));
	}

}
