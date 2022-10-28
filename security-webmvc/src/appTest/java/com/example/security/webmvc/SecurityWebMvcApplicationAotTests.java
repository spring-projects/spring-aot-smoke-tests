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

package com.example.security.webmvc;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class SecurityWebMvcApplicationAotTests {

	@Test
	void anonymousShouldBeAccessibleWithoutCredentials(WebTestClient client) {
		client.get().uri("/rest/anonymous").exchange().expectStatus().isOk().expectBody().consumeWith(
				(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("anonymous"));
	}

	@Test
	void authorizedShouldBeProtectedWithoutCredentials(WebTestClient client) {
		client.get().uri("/rest/authorized").exchange().expectStatus().isUnauthorized();
	}

	@Test
	void authorizedShouldBeAccessibleWithCredentials(WebTestClient client) {
		client.get().uri("/rest/authorized").headers((header) -> header.setBasicAuth("user", "password")).exchange()
				.expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.isEqualTo("authorized: user"));
	}

	@Test
	void authorizedShouldBeProtectedWithWrongCredentials(WebTestClient client) {
		client.get().uri("/rest/authorized").headers((header) -> header.setBasicAuth("wrong-user", "wrong-password"))
				.exchange().expectStatus().isUnauthorized();
	}

	@Test
	void adminShouldBeProtectedWithoutCredentials(WebTestClient client) {
		client.get().uri("/rest/admin").exchange().expectStatus().isUnauthorized();
	}

	@Test
	void adminShouldBeAccessibleWithCredentials(WebTestClient client) {
		client.get().uri("/rest/admin").headers((header) -> header.setBasicAuth("admin", "password")).exchange()
				.expectStatus().isOk().expectBody().consumeWith(
						(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("admin: admin"));
	}

	@Test
	void adminShouldBeProtectedWithWrongCredentials(WebTestClient client) {
		client.get().uri("/rest/admin").headers((header) -> header.setBasicAuth("wrong-admin", "wrong-password"))
				.exchange().expectStatus().isUnauthorized();
	}

	@Test
	void adminShouldBeProtectedWithWrongRole(WebTestClient client) {
		client.get().uri("/rest/admin").headers((header) -> header.setBasicAuth("user", "password")).exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	void staticResourcesShouldBeProtected(WebTestClient client) {
		client.get().uri("/foo.html").exchange().expectStatus().isUnauthorized();
		client.get().uri("/bar.html").exchange().expectStatus().isUnauthorized();
	}

}
