package com.example.mail;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class MailApplicationAotTests {

	@Test
	void expectedLoggingIsProduced(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining(
					"Sent message SimpleMailMessage: from=alice@localhost; replyTo=null; to=bob@localhost; cc=; bcc=; sentDate=null; subject=null; text=Dear Bob, hello world! Alice.");
		});
	}

}
