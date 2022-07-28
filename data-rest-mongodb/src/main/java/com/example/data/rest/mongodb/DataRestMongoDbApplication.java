package com.example.data.rest.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataRestMongoDbApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataRestMongoDbApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
