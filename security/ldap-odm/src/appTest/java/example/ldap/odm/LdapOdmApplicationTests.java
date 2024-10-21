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

package example.ldap.odm;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApplicationTest
public class LdapOdmApplicationTests {

	@Test
	void shouldFindAllPeople(WebTestClient client) {
		client.get().uri("/person").exchange().expectBody().json("""
				  [
					  {
						  "company": "company1",
						  "country": "Sweden",
						  "description": "Sweden, Company1, Some Person",
						  "fullName": "Some Person",
						  "lastName": "Person",
						  "phone": "+46 555-123456"
					  },
					  {
						  "company": "company1",
						  "country": "Sweden",
						  "description": "Sweden, Company1, Some Person2",
						  "fullName": "Some Person2",
						  "lastName": "Person2",
						  "phone": "+46 555-654321"
					  }
				  ]
				""");
	}

	@Test
	void shouldFindPerson(WebTestClient client) {
		// @formatter:off
		client.get().uri((builder) -> builder.path("/person")
						.queryParam("country", "Sweden")
						.queryParam("company", "company1")
						.queryParam("fullName", "Some Person2").build()
				).exchange().expectBody().json("""
						{
						  "company": "company1",
						  "country": "Sweden",
						  "description": "Sweden, Company1, Some Person2",
						  "fullName": "Some Person2",
						  "lastName": "Person2",
						  "phone": "+46 555-654321"
						}
				""");
		// @formatter:on
	}

	@Test
	void shouldAddNewPerson(WebTestClient client) {
		client.post().uri("/person").exchange().expectStatus().isOk();
	}

	@Test
	void shouldDeletePerson(WebTestClient client) {
		// @formatter:off
		client.delete().uri((builder) -> builder.path("/person")
				.queryParam("country", "Sweden")
				.queryParam("company", "company1")
				.queryParam("fullName", "Some Person2").build()
		).exchange().expectStatus().isOk();
		// @formatter:on
	}

}
