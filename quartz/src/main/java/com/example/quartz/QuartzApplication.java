package com.example.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(QuartzApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
