package com.example.jdbc.postgresql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcPostgreSQLApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(JdbcPostgreSQLApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
