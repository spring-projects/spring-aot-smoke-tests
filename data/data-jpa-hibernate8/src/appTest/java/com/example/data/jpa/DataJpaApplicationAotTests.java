/*
 * Copyright 2026-present the original author or authors.
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
			assertThat(output).hasLineMatching(".*left join \\(?book_authors a1_0 .*")
				.hasSingleLineContaining(
						"namedEntityGraph: Book{title='Spring in Action', authors=[Author{name='Craig Walls'}]}");
		});
	}

	@Test
	void abstractPersistable(AssertableOutput output) {

		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineMatching(".*Publisher\\{name='independently published', id=\\d.*\\}");
		});
	}

	@Test
	void listVouchers(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining(
						"listVouchers(): voucher = Voucher{id=1, msisdn='0810000000', status=0, dateCreated")
				.hasSingleLineContaining(
						"listVouchers(): voucher = Voucher{id=2, msisdn='0810000000', status=1, dateCreated=");

		});
	}

	@Test
	void listRecipients(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining(
					"listRecipients(): recipient = Recipient{id=1, address=Address[street=Paul Bert, city=Lyon, postalCode=69003]}");

		});
	}

	@Test
	void fragmentsApi(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("fragmentsApi(): RepositoryExtension says hello to Book");
		});
	}

	@Test
	void typedPropertyPath(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("typedPropertyPath(): 1,0");
		});
	}

	@Test
	void pageQuery(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("pagedVouchers(): total = 2, pages = 2, statuses = [0]")
				.hasLineMatching(
						".*select .*from voucher .*order by v1_0.status offset \\? rows fetch first \\? rows only.*");
		});
	}

	@Test
	void sliceQuery(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("slicedVouchers(): hasNext = true, statuses = [0]");
		});
	}

	@Test
	void limitParameter(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("limitedVouchers(): size = 1");
		});
	}

	@Test
	void derivedLimit(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("topVouchers(): statuses = [1]")
				.hasLineMatching(".*select .*from voucher .*order by v1_0.status desc fetch first \\? rows only.*");
		});
	}

	@Test
	void existsQuery(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("existsVouchers(): known = true, unknown = false");
		});
	}

	@Test
	void lockMode(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("findForUpdate(): authors = [Author{name='Josh Long'}]")
				.hasLineMatching(".*select .*from author .*where a1_0.name=\\? for update.*");
		});
	}

	@Test
	void modifyingQuery(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("modifyingUpdate(): updated = 1")
				.hasLineMatching(".*update author a1_0 set name=\\? where a1_0.id=\\?.*");
		});
	}

	@Test
	void modifyingQueryWithoutResult(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("modifyingDelete(): count = 0")
				.hasLineMatching(".*delete from publisher p1_0 where p1_0.name=\\?.*");
		});
	}

	@Test
	void derivedDelete(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("derivedDelete(): removed = 1");
		});
	}

}
