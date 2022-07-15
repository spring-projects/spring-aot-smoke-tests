package com.example.log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Log4j2Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Log4j2Application.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
