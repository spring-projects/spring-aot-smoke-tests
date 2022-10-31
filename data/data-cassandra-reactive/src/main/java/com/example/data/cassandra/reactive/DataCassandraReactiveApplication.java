package com.example.data.cassandra.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataCassandraReactiveApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataCassandraReactiveApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
