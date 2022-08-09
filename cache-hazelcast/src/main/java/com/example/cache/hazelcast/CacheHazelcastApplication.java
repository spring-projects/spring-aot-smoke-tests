package com.example.cache.hazelcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CacheHazelcastApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CacheHazelcastApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
