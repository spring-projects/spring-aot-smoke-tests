package com.example.data.mongodb.reactive;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class DataMongoDbReactiveApplicationAotTests {

	@Test
	void auditing(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("lastModifiedBy=Douglas Adams");
		});
	}

	@Test
	void pagingAndSorting(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("sortedByCustomer: [Order{id=");
		});
	}

	@Test
	void derivedQuery(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("derivedQuery: Order{id=");
		});
	}

	@Test
	void annotatedQuery(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("annotatedQuery: Order{id=");
		});
	}

	@Test
	void aggregation(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining(
					"aggregation: [OrdersPerCustomer{customerId='c42', total=3}, OrdersPerCustomer{customerId='b12', total=2}]");
		});
	}

	@Test
	void customRepositoryImplementation(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("invoice: Invoice{orderId=");
		});
	}

	@Test
	void resultProjection(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("projection: OrderProjection($Proxy");
		});
	}

	@Test
	void queryByExample(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("qbe: Order{id=");
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
