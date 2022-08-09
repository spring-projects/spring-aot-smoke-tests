package com.example.cache.caffeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CacheCaffeineApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CacheCaffeineApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
