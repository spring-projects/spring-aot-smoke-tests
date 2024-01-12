package com.example.disposable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DisposableApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisposableApplication.class, args);
	}

	@Bean
	DisposableInterface disposableInterface() {
		return new DisposableBean();
	}

}
