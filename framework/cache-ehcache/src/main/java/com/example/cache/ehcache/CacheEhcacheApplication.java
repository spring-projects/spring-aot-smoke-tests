package com.example.cache.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CacheEhcacheApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CacheEhcacheApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
