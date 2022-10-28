package com.example.cloud.loadbalancing;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class CloudLoadBalancerClientWebFluxApplicationAotTests {

	@Test
	void shouldRetrieveInstance(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("On Start: ");
			assertThat(output).hasLineContaining("On Start Request: ");
			assertThat(output).hasLineContaining("On Complete: ");
			assertThat(output).hasLineContaining("c.e.c.l.s.LoadBalancerClientTestService  : demo");
			assertThat(output).hasLineContaining("c.e.c.l.s.LoadBalancerClientTestService  : test");
			assertThat(output).hasNoLinesContaining("ERROR");
		});

	}

}
