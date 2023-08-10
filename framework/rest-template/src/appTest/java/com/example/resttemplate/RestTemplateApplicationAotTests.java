package com.example.resttemplate;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class RestTemplateApplicationAotTests {

	@Test
	@DisabledIfEnvironmentVariable(named = "CI", matches = "true", disabledReason = "HTTP is blocked on CI")
	void httpWorks(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.untilAsserted(() -> assertThat(output)
					.hasLineMatching("http: DataDto\\{url='http:\\/\\/[\\w.]+:\\d+\\/anything', method='GET'\\}"));
	}

	@Test
	void httpsWorks(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.untilAsserted(() -> assertThat(output)
					.hasLineMatching("https: DataDto\\{url='https:\\/\\/[\\w.]+:\\d+\\/anything', method='GET'\\}"));
	}

}
