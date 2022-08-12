package com.example.transactional;

import org.springframework.aot.smoketest.thirdpartyhints.HikariRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(HikariRuntimeHints.class)
public class TransactionalApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TransactionalApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
