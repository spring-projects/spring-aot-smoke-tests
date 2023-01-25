package com.example.spring.orm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HibernateEnhancedApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(HibernateEnhancedApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
