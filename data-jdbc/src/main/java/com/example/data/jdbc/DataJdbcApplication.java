package com.example.data.jdbc;

import org.springframework.aot.smoketest.thirdpartyhints.HikariRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(HikariRuntimeHints.class)
public class DataJdbcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataJdbcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
