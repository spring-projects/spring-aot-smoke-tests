package com.example.cloud.openfeign;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class CloudOpenFeignApplicationAotTests {

	@Test
	void shouldCallTestService(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("c.e.c.o.CloudOpenFeignApplication        : test");
			assertThat(output).hasLineContaining("c.e.c.o.CloudOpenFeignApplication        : test type hints");
			// verify custom config with Logger.Level.FULL loaded
			assertThat(output).hasLineContaining("---> POST http://test-service/test HTTP/1.1");
			assertThat(output).hasNoLinesContaining("ERROR");
		});

	}

}
