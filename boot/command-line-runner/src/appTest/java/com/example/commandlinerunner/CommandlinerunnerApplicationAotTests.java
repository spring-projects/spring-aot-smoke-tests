package com.example.commandlinerunner;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class CommandlinerunnerApplicationAotTests {

	@Test
	void expectedLoggingIsProduced(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("INFO log message")
					.hasSingleLineContaining("WARNING log message").hasSingleLineContaining("ERROR log message")
					.hasNoLinesContaining("TRACE log message").hasNoLinesContaining("DEBUG log message")
					.hasSingleLineContaining("Hello from MyServiceImpl");
		});
	}

	@Test
	void bannerIsPrinted(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("= Command line runner smoke test =");
		});
	}

}
