package com.example.cloud.discovery.eureka;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class EurekaDiscoveryClientApplicationAotTests {

	@Test
	void shouldRegisterWithEurekaServer(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("registration status: 204");
			assertThat(output).hasNoLinesContaining("Exception");
		});
	}

}
