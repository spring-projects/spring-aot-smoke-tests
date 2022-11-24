package com.example.actuator.webflux;

import java.util.Map;

import com.example.actuator.webflux.health.AnotherCustomHealthIndicator;
import com.example.actuator.webflux.health.CustomHealthIndicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActuatorWebFluxApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ActuatorWebFluxApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean("composite")
	CompositeReactiveHealthContributor compositeReactiveHealthContributor(CustomHealthIndicator customHealthIndicator,
			AnotherCustomHealthIndicator anotherCustomHealthIndicator) {
		return CompositeReactiveHealthContributor
				.fromMap(Map.of("custom", customHealthIndicator, "another-custom", anotherCustomHealthIndicator));
	}

}
