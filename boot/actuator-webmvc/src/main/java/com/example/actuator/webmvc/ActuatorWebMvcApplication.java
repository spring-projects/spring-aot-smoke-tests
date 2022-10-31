package com.example.actuator.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActuatorWebMvcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ActuatorWebMvcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
