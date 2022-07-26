package com.example.eventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class HelloEventListener {

	@EventListener
	public void onHelloEvent(HelloEvent helloEvent) {
		System.out.printf("HelloEventListener: Got event %s%n", helloEvent);
	}

}
