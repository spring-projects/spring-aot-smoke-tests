package com.example.data.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataCassandraApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataCassandraApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
