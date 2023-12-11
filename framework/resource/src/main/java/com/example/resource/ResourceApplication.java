package com.example.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResourceApplication {

	private static final Log logger = LogFactory.getLog(ResourceApplication.class);

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ResourceApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	public ApplicationRunner run(TestMethods testMethods, TestFields testFields) {
		return (args) -> {
			logger.info("Methods: " + testMethods.describe());
			logger.info("Fields: " + testFields.describe());
		};
	}

}
