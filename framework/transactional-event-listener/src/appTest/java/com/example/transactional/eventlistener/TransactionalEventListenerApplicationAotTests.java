package com.example.transactional.eventlistener;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class TransactionalEventListenerApplicationAotTests {

	@Test
	void eventIsPublishedIfTransactionIsCommited(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining(
						"TransactionalEventPublisher: Publishing TransactionalEvent (transaction successful)")
				.hasSingleLineContaining(
						"TransactionalEventListener: Got event TransactionalEvent{greeting='TX successful'}");
		});
	}

	@Test
	void eventIsNotPublishedIfTransactionIsAborted(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining(
						"TransactionalEventPublisher: Publishing TransactionalEvent (transaction failed)")
				.hasNoLinesContaining("TransactionalEventListener: Got event TransactionalEvent{greeting='TX failed'}");
		});
	}

}
