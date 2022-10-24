package com.example.data.cassandra;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;
import org.springframework.data.domain.AuditorAware;

@SpringBootApplication
@EnableCassandraAuditing(auditorAwareRef = "fixedAuditor")
public class DataCassandraApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataCassandraApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	AuditorAware<String> fixedAuditor() {
		return () -> Optional.of("Douglas Adams");
	}

}
