/*
 * Copyright 2022 the original author or authors.
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

package com.example.hateoas;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class HateoasApplicationAotTests {

	@Test
	void employeeHasLinks(WebTestClient client) {
		client.get()
			.uri("/employee/1")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.id")
			.isEqualTo("1")
			.jsonPath("$._links.self.href")
			.value(v -> {
				assertThat(v).isInstanceOf(String.class);
				assertThat((String) v).endsWith("/employee/1");
			})
			.jsonPath("$._links.manager.href")
			.value(v -> {
				assertThat(v).isInstanceOf(String.class);
				assertThat((String) v).endsWith("/manager/1");
			});
	}

	@Test
	void managerHasLinks(WebTestClient client) {
		client.get()
			.uri("/manager/1")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.id")
			.isEqualTo("1")
			.jsonPath("$._links.self.href")
			.value(v -> {
				assertThat(v).isInstanceOf(String.class);
				assertThat((String) v).endsWith("/manager/1");
			})
			.jsonPath("$._links.reports.href")
			.value(v -> {
				assertThat(v).isInstanceOf(String.class);
				assertThat((String) v).endsWith("/manager/1/reports");
			});
	}

	@Test
	void reportsIsCollection(WebTestClient client) {
		client.get()
			.uri("/manager/1/reports")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$._links.self.href")
			.value(v -> {
				assertThat(v).isInstanceOf(String.class);
				assertThat((String) v).endsWith("/manager/1/reports");
			})
			.jsonPath("$._embedded")
			.isMap()
			.jsonPath("$._embedded.employees")
			.isArray()
			.jsonPath("$._embedded.employees[0].id")
			.isEqualTo("1")
			.jsonPath("$._embedded.employees[1].id")
			.isEqualTo("2")
			.jsonPath("$._embedded.employees[2].id")
			.isEqualTo("3");
	}

}
