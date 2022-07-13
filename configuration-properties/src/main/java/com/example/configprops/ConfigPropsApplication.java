package com.example.configprops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigPropsApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ConfigPropsApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
