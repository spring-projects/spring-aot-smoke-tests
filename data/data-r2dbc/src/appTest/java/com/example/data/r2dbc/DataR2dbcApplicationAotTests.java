package com.example.data.r2dbc;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class DataR2dbcApplicationAotTests {

	@Test
	void findAll(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("findAll(): Reservation{id=1, name='reservation-1'}")
					.hasSingleLineContaining("findAll(): Reservation{id=2, name='reservation-2'}");
		});
	}

	@Test
	void save(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("save(): Reservation{id=3, name='reservation-3'}");
		});
	}

}
