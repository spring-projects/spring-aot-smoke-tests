package com.example.aspect;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class AspectApplicationAotTests {

	@Test
	void shouldInterceptMethodA(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(10))
			.untilAsserted(() -> assertThat(output).hasSingleLineContaining("methodA: A-from-aspect")
				.hasSingleLineContaining("methodB: B"));
	}

}
