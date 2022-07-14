package com.example.jdbc;

import org.springframework.aot.smoketest.thirdpartyhints.HikariRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(HikariRuntimeHints.class)
public class JdbcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(JdbcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
