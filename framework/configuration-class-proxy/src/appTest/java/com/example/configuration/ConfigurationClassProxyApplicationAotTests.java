package com.example.configuration;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class ConfigurationClassProxyApplicationAotTests {

	@Test
	void expectedLoggingIsProduced(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(10))
			.untilAsserted(() -> assertThat(output).hasSingleLineContaining("Main: Hello0 World")
				.hasSingleLineContaining("Nested: Nested Hello0 World"));
	}

}
