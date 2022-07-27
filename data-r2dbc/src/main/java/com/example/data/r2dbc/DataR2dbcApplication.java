package com.example.data.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataR2dbcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataR2dbcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
