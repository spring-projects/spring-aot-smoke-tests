package com.example.data.mongodb.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableReactiveMongoAuditing(auditorAwareRef = "fixedAuditor")
public class DataMongoDbReactiveApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataMongoDbReactiveApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	ReactiveAuditorAware<String> fixedAuditor() {
		return () -> Mono.just("Douglas Adams");
	}

}
