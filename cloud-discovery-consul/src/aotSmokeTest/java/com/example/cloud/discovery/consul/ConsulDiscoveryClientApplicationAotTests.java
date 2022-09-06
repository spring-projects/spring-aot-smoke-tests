package com.example.cloud.discovery.consul;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class ConsulDiscoveryClientApplicationAotTests {

	@Test
	void shouldRegisterWithConsul(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("Registering service with consul");
			assertThat(output).hasNoLinesContaining("Error registering service with consul");
		});

	}

}
