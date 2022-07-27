package com.example.data.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataMongoDbApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataMongoDbApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
