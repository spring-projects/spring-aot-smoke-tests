package com.example.tracing.brave.zipkin;

import java.time.Duration;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private final ObservationRegistry observationRegistry;

	public CLR(ObservationRegistry observationRegistry) {
		this.observationRegistry = observationRegistry;
	}

	@Override
	public void run(String... args) throws Exception {
		Observation observation = Observation.start("test-observation", this.observationRegistry);
		try {
			observation.lowCardinalityKeyValue("key-1", "value-1");
			System.out.println("Before sleep");
			Thread.sleep(Duration.ofSeconds(2).toMillis());
			System.out.println("After sleep");
		}
		finally {
			observation.stop();
		}
		System.out.println("Observation finished");
	}

}
