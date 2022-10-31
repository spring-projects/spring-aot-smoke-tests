package com.example.transactional.eventlistener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public TransactionalEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional
	public void transactionSuccessful() {
		System.out.println("TransactionalEventPublisher: Publishing TransactionalEvent (transaction successful)");
		this.applicationEventPublisher.publishEvent(new TransactionalEvent("TX successful"));
	}

	@Transactional
	public void transactionFailed() {
		System.out.println("TransactionalEventPublisher: Publishing TransactionalEvent (transaction failed)");
		this.applicationEventPublisher.publishEvent(new TransactionalEvent("TX failed"));
		// This causes the transaction to abort
		throw new RuntimeException("Boom");
	}

}
