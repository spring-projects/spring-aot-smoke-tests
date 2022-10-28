package com.example.order;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class OrderApplicationAotTests {

	@Test
	void expectedOrderIsLogged(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(
				() -> assertThat(output).hasSingleLineContaining("Items: priority50, -20, -10, 10, none"));
	}

}
