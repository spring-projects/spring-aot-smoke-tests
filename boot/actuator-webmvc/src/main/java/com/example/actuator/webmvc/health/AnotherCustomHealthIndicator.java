package com.example.actuator.webmvc.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AnotherCustomHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		return Health.up().build();
	}

}
