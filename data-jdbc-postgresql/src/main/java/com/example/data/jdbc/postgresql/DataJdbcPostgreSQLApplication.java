package com.example.data.jdbc.postgresql;

import org.springframework.aot.smoketest.thirdpartyhints.HikariRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(HikariRuntimeHints.class)
public class DataJdbcPostgreSQLApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataJdbcPostgreSQLApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
