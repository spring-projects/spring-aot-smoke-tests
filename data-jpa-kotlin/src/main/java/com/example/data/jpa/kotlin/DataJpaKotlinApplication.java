package com.example.data.jpa.kotlin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataJpaKotlinApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataJpaKotlinApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
