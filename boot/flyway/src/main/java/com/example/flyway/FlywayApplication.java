package com.example.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlywayApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(FlywayApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
