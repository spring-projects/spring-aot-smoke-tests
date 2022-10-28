package com.example.jdbc.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcMySQLApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(JdbcMySQLApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
