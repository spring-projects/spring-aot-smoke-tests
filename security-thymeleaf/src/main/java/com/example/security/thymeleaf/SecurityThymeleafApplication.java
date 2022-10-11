package com.example.security.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
public class SecurityThymeleafApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(SecurityThymeleafApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
