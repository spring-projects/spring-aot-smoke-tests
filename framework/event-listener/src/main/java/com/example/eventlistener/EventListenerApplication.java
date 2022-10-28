package com.example.eventlistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventListenerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(EventListenerApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
