package com.example.cache.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CacheRedisApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CacheRedisApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
