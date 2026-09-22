package com.example.singlebuildpacks.app;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SingleBuildpacksApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SingleBuildpacksApplication.class);
		application.run(args);
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> System.out.println("Hello Native");
	}

}
