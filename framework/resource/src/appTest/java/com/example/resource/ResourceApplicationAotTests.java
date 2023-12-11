package com.example.resource;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class ResourceApplicationAotTests {

	@Test
	void expectedResourcesAreLogged(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(10))
			.untilAsserted(
					() -> assertThat(output).hasSingleLineContaining("Methods: hello='Hello',test='Test',number=42")
						.hasSingleLineContaining("Fields: hello='Hello',test='Test',number=42"));
	}

}
