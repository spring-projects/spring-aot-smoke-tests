package com.example.actuator.webmvc;

import java.time.Duration;

import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class RegisterCustomTimer implements CommandLineRunner {

	private final MeterRegistry meterRegistry;

	RegisterCustomTimer(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	@Override
	public void run(String... args) {
		this.meterRegistry.timer("custom.timer").record(Duration.ofSeconds(5));
	}

}
