package com.example.aspect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AspectApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(AspectApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	CommandLineRunner commandLineRunner(TestComponent testComponent) {
		return args -> {
			System.out.println("methodA: " + testComponent.methodA());
			System.out.println("methodB: " + testComponent.methodB());
		};
	}

}
