package com.example.cloud.discovery.zookeeper;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class ZookeeperDiscoveryClientApplicationAotTests {

	@Test
	void shouldRegisterWithZookeeper(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.untilAsserted(() -> assertThat(output).hasLineContaining("Session establishment complete on server"));

	}

}
