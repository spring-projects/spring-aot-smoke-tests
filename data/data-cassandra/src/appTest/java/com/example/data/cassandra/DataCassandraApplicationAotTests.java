package com.example.data.cassandra;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

@ApplicationTest
class DataCassandraApplicationAotTests {

	@Test
	void auditing(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("modifiedBy=Douglas Adams");
		});
	}

	@Test
	void resultSorting(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("sortedByCustomer: [Order{id='o1', customerId='c42'");
		});
	}

	@Test
	void resultSlice(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("c42_slice0: Slice 0");
		});
	}

	@Test
	void derivedQuery(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("derivedQuery: Order{id=");
		});
	}

	@Test
	void resultProjection(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("OrderProjection($Proxy");
		});
	}

	@Test
	void findAll(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("findAll(): Person{firstname='first-1', lastname='last-1'}")
				.hasSingleLineContaining("findAll(): Person{firstname='first-2', lastname='last-2'}")
				.hasSingleLineContaining("findAll(): Person{firstname='first-3', lastname='last-3'}");
		});
	}

	@Test
	void findByLastName(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining("findByLastname(): Person{firstname='first-3', lastname='last-3'}");
		});
	}

}
