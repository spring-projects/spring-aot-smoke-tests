package com.example.security.method;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityMethodApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SecurityMethodApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
