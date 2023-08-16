package com.example.servlet.componentscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServletComponentScanApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServletComponentScanApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
