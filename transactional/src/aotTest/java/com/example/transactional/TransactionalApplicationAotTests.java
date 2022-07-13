package com.example.transactional;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class TransactionalApplicationAotTests {

	@Test
	void transactionShouldBeActive(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10))
				.untilAsserted(() -> assertThat(output).hasSingleLineContaining("Transaction active: true"));
	}

}
