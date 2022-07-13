package com.example.configprops;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class ConfigPropsApplicationAotTests {

	@Test
	void stringShouldBind(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("appProperties.getString(): a-string");
		});
	}

	@Test
	void dataSizeShouldBind(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("appProperties.getDataSize(): 1048576B");
		});
	}

	@Test
	void stringListShouldBind(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("appProperties.getStringList(): [string-1, string-2]");
		});
	}

	@Test
	void nestedListShouldBind(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("appProperties.getNestedList(): [Nested{aInt=1}, Nested{aInt=2}]");
		});
	}

	@Test
	void nestedShouldBind(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("appProperties.getNested(): Nested{aInt=3}");
		});
	}

	@Test
	void nestedIntShouldBind(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("appProperties.getNested().getaInt(): 3");
		});
	}

}
