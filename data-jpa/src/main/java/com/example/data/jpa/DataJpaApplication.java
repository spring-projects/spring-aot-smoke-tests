package com.example.data.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataJpaApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
