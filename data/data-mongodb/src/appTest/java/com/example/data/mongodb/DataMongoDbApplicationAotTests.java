package com.example.data.mongodb;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class DataMongoDbApplicationAotTests {

	@Test
	void aggregation(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining(
					"aggregation: [OrdersPerCustomer{customerId='c42', total=3}, OrdersPerCustomer{customerId='b12', total=2}]");
		});
	}

	@Test
	void resultProjection(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("OrderProjection($Proxy");
		});
	}

	@Test
	void customConversions(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining(
					"Raw Document: Document{{_id=c-1, name=c; 42, _class=com.example.data.mongodb.Customer}}");
		});
	}

	@Test
	void documentReference(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("document ref (no proxy): 30.0")
				.hasLineMatching("lazy document ref .*\\$LazyLoadingProxy")
				.hasLineContaining("lazy document ref (resolved): 30.0")
				.hasLineContaining("lazy document ref (resolved): 30.0");
		});
	}

	@Test
	void queryByExample(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("qbe: [Order{id=");
		});
	}

	@Test
	void transactionSupport(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("in-transaction: Order{id='")
				.hasSingleLineContaining("transactional-status: rollback")
				.hasSingleLineContaining("after-transaction: []");
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
