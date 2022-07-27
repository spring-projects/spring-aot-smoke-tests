package com.example.data.mongodb.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataMongoDbReactiveApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataMongoDbReactiveApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
