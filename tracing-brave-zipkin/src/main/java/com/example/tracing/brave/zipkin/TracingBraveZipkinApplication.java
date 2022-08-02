package com.example.tracing.brave.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TracingBraveZipkinApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TracingBraveZipkinApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
