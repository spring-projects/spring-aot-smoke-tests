/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
