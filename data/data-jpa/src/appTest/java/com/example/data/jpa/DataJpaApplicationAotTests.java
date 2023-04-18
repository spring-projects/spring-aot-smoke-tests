package com.example.data.jpa;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class DataJpaApplicationAotTests {

	@Test
	void insert(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("insertAuthors(): author1 = Author{name='Josh Long'}")
				.hasSingleLineContaining("insertAuthors(): author2 = Author{name='Martin Kleppmann'}");
		});
	}

	@Test
	void listAllAuthors(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("listAllAuthors(): author = Author{name='Josh Long'")
				.hasSingleLineContaining("Book{title='Cloud Native Java'")
				.hasSingleLineContaining("Book{title='Reactive Spring'")
				.hasSingleLineContaining("listAllAuthors(): author = Author{name='Martin Kleppmann'}")
				.hasSingleLineContaining("Book{title='Designing Data Intensive Applications'");
		});
	}

	@Test
	void findById(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("findById(): author1 = Author{name='Josh Long'}")
				.hasSingleLineContaining("findById(): author2 = Author{name='Martin Kleppmann'}");
		});
	}

	@Test
	void queryDerivedFromMethodName(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("findByPartialName(): author1 = Author{name='Josh Long'}")
				.hasSingleLineContaining("findByPartialName(): author2 = Author{name='Martin Kleppmann'}");
		});
	}

	@Test
	void queryAnnotatedMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("queryFindByName(): author1 = Author{name='Josh Long'}")
				.hasSingleLineContaining("queryFindByName(): author2 = Author{name='Martin Kleppmann'}");
		});
	}

	@Test
	void streamingResult(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("streamAuthors(): author = Author{name='Martin Kleppmann'}")
				.hasNoLinesContaining("streamAuthors(): author = Author{name='Josh Long'}");
		});
	}

	@Test
	void deleteAll(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("deleteAll(): count = 0");
		});
	}

	@Test
	void callback(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineContaining("Pre remove author");
		});
	}

	@Test
	void entityGraph(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasLineContaining("left join (book_authors a1_0 join author a1_1 on a1_1.id=a1_0.authors_id)")
				.hasSingleLineContaining(
						"namedEntityGraph: Book{title='Spring in Action', authors=[Author{name='Craig Walls'}]}");
		});
	}

}
