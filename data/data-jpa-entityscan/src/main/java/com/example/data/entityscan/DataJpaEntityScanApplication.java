package com.example.data.entityscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataJpaEntityScanApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataJpaEntityScanApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
