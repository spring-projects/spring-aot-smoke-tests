package com.example.cache.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CacheSimpleApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CacheSimpleApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
