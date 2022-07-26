package com.example.eventlistener;

class HelloEvent {

	private final String greeting;

	HelloEvent(String greeting) {
		this.greeting = greeting;
	}

	String getGreeting() {
		return greeting;
	}

	@Override
	public String toString() {
		return "HelloEvent{" + "greeting='" + greeting + '\'' + '}';
	}

}
