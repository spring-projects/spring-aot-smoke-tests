package com.example.multibuildpacks.app;

import com.example.multibuildpacks.lib.MultiBuildpacksService;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultiBuildpacksApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MultiBuildpacksApplication.class);
		application.run(args);
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> new MultiBuildpacksService().sayHello();
	}

}
