package com.example.commandlinerunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.aot.smoketest.support.assertj.Output;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

@AotSmokeTest
class CommandlinerunnerApplicationAotTests {

	@Test
	void expectedLoggingIsProduced(Output output) throws InterruptedException {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("INFO log message")
					.hasSingleLineContaining("WARNING log message").hasSingleLineContaining("ERROR log message")
					.hasNoLinesContaining("TRACE log message").hasNoLinesContaining("DEBUG log message");
		});
	}

}
