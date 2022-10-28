package com.example.freemarker.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreemarkerWebFluxApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(FreemarkerWebFluxApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
