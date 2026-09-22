package com.example.multinbt.app;

import com.example.multinbt.lib.MultiNbtService;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultiNbtApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MultiNbtApplication.class);
		application.run(args);
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> new MultiNbtService().sayHello();
	}

}
