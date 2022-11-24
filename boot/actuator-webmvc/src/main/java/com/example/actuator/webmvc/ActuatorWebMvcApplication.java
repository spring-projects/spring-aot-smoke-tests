package com.example.actuator.webmvc;

import java.util.Map;

import com.example.actuator.webmvc.health.AnotherCustomHealthIndicator;
import com.example.actuator.webmvc.health.CustomHealthIndicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActuatorWebMvcApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ActuatorWebMvcApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean("composite")
	CompositeHealthContributor compositeHealthContributor(CustomHealthIndicator customHealthIndicator,
			AnotherCustomHealthIndicator anotherCustomHealthIndicator) {
		return CompositeHealthContributor
				.fromMap(Map.of("custom", customHealthIndicator, "another-custom", anotherCustomHealthIndicator));
	}

}
