package com.example.cache.ehcache;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class CacheEhcacheApplicationAotTests {

	@Test
	void methodIsCachedOnClasses(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("class.invoke: 1").hasNoLinesContaining("class.invoke: 2");
		});
	}

	@Test
	void methodIsCachedOnInterfaces(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("interface.invoke: 1")
					.hasNoLinesContaining("interface.invoke: 2");
		});
	}

}
