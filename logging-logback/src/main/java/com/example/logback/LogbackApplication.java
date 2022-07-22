package com.example.logback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogbackApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LogbackApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
