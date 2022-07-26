package com.example.transactional.eventlistener;

class TransactionalEvent {

	private final String greeting;

	TransactionalEvent(String greeting) {
		this.greeting = greeting;
	}

	String getGreeting() {
		return greeting;
	}

	@Override
	public String toString() {
		return "TransactionalEvent{" + "greeting='" + greeting + '\'' + '}';
	}

}
