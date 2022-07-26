package com.example.eventlistener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class HelloEventPublisher implements CommandLineRunner {

	private final ApplicationEventPublisher applicationEventPublisher;

	public HelloEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("HelloEventPublisher: Publishing HelloEvent");
		this.applicationEventPublisher.publishEvent(new HelloEvent("Hello world"));
	}

}
