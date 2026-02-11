package com.example.singlenbt.app;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SingleNbtApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SingleNbtApplication.class);
		application.run(args);
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> System.out.println("Hello Native");
	}

}
