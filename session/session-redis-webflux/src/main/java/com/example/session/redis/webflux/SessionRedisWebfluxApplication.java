package com.example.session.redis.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SessionRedisWebfluxApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SessionRedisWebfluxApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
