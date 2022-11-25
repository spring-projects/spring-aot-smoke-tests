package com.example.logback.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogbackXmlApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LogbackXmlApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
