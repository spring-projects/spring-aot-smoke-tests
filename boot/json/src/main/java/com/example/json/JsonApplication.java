package com.example.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(JsonApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
