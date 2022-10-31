package com.example.freemarker.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreemarkerWebMvcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(FreemarkerWebMvcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
