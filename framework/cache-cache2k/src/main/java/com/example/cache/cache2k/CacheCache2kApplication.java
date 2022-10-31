package com.example.cache.cache2k;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CacheCache2kApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CacheCache2kApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
