package com.example.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigurationClassProxyApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ConfigurationClassProxyApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
