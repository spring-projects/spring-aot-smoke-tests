package com.example.logback;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class LogbackApplicationAotTests {

	@Test
	void expectedLoggingIsProduced(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasNoLinesContaining("Trace message").hasSingleLineContaining("Debug message")
					.hasSingleLineContaining("Info message").hasSingleLineContaining("Warn message")
					.hasSingleLineContaining("Error message").hasSingleLineContaining("Info with parameters: 1")
					.hasSingleLineContaining("Error with stacktrace")
					.hasSingleLineContaining("java.lang.RuntimeException: Boom")
					.hasSingleLineContaining("at com.example.logback.CLR.run(CLR.java");
		});
	}

}
