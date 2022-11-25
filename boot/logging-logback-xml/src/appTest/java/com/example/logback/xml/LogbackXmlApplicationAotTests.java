package com.example.logback.xml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class LogbackXmlApplicationAotTests {

	@Test
	void expectedLoggingIsProduced(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> {
			assertThat(output).hasNoLinesContaining("Trace message")
					.hasSingleLineContaining("main | DEBUG | com.example.logback.xml.CLR | Debug message")
					.hasSingleLineContaining("main | INFO  | com.example.logback.xml.CLR | Info message")
					.hasSingleLineContaining("main | WARN  | com.example.logback.xml.CLR | Warn message")
					.hasSingleLineContaining("main | ERROR | com.example.logback.xml.CLR | Error message")
					.hasSingleLineContaining("main | INFO  | com.example.logback.xml.CLR | Info with parameters: 1")
					.hasSingleLineContaining("main | ERROR | com.example.logback.xml.CLR | Error with stacktrace")
					.hasSingleLineContaining("java.lang.RuntimeException: Boom")
					.hasSingleLineContaining("at com.example.logback.xml.CLR.run(CLR.java");
		});
	}

	@Test
	void logFileIsWritten() throws IOException {
		String tmpDir = System.getProperty("java.io.tmpdir");
		assertThat(tmpDir).isNotNull();
		String lines = Files.readString(Path.of(tmpDir, "logback-logback-xml.log"), StandardCharsets.UTF_8);
		assertThat(lines).contains("main | DEBUG | com.example.logback.xml.CLR | Debug message");
	}

}
