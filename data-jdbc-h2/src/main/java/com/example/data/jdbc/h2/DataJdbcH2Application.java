package com.example.data.jdbc.h2;

import org.springframework.aot.smoketest.thirdpartyhints.HikariRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(HikariRuntimeHints.class)
public class DataJdbcH2Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataJdbcH2Application.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
