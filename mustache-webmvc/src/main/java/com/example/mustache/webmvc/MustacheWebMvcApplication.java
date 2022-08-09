package com.example.mustache.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MustacheWebMvcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MustacheWebMvcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
