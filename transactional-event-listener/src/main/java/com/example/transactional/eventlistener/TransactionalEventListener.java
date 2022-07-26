package com.example.transactional.eventlistener;

import org.springframework.stereotype.Component;

@Component
class TransactionalEventListener {

	@org.springframework.transaction.event.TransactionalEventListener
	public void onTransactionalEvent(TransactionalEvent transactionalEvent) {
		System.out.printf("TransactionalEventListener: Got event %s%n", transactionalEvent);
	}

}
