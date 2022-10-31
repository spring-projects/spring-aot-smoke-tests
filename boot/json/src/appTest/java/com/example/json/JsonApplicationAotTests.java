package com.example.json;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class JsonApplicationAotTests {

	@Test
	void jsonSerializationIsWorking(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("DTO: {\"field\":\"field-1\"}");
		});
	}

	@Test
	void jsonComponentIsWorking(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("DTO2: {\"customized-field\":\"field-2\"}");
		});
	}

	@Test
	void jsonMixinIsWorking(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("DTO3: {\"mixin-field\":\"field-3\"}");
		});
	}

}
