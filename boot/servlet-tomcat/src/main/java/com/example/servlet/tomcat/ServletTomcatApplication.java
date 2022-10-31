package com.example.servlet.tomcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServletTomcatApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServletTomcatApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
