package com.example.thymeleaf.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThymeleafWebMvcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ThymeleafWebMvcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
