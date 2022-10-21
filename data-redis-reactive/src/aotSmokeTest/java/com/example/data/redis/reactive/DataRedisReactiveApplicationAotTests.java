package com.example.data.redis.reactive;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class DataRedisReactiveApplicationAotTests {

	@Test
	void connectionTest(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("connection: OK");
		});
	}

	@Test
	void templateOps(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("template ops: OK");
		});
	}

	@Test
	void findAll(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("1: Person{firstname='first-1', lastname='last-1'}")
					.hasSingleLineContaining("2: Person{firstname='first-2', lastname='last-2'}")
					.hasSingleLineContaining("3: Person{firstname='first-3', lastname='last-3'}");
		});
	}

	@Test
	void pubSub(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("pub/sub: payload");
		});
	}

}
