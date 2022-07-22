package com.example.webclient;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class WebClientApplicationAotTests {

	@Test
	void httpWorks(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("http: DataDto{url='http://httpbin.org/anything', method='GET'}");
		});
	}

	@Test
	void httpsWorks(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("https: DataDto{url='https://httpbin.org/anything', method='GET'}");
		});
	}

}
