package com.example.scheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduledApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ScheduledApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
