package com.example.transactional.eventlistener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final TransactionalEventPublisher publisher;

	CLR(TransactionalEventPublisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public void run(String... args) throws Exception {
		this.publisher.transactionSuccessful();
		try {
			this.publisher.transactionFailed();
		}
		catch (RuntimeException ex) {
			// Swallow, as this is expected
		}
	}

}
