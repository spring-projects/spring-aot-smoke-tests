package com.example.thymeleaf.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThymeleafWebFluxApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ThymeleafWebFluxApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
