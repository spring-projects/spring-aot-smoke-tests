package com.example.jdbc.h2;

import org.springframework.aot.smoketest.thirdpartyhints.HikariRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(HikariRuntimeHints.class)
public class JdbcH2Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(JdbcH2Application.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
