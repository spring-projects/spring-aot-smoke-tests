package com.example.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(BatchApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
