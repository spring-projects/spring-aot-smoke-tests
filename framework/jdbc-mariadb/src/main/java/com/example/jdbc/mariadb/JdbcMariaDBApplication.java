package com.example.jdbc.mariadb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcMariaDBApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(JdbcMariaDBApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
