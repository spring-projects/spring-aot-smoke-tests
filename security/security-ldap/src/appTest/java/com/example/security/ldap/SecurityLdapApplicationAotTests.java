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

package com.example.security.ldap;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class SecurityLdapApplicationAotTests {

	@Test
	void anonymousShouldBeUnauthorizedWithoutCredentials(WebTestClient client) {
		client.get().uri("/").exchange().expectStatus().isUnauthorized();
	}

	@Test
	void homeShouldShowUsername(WebTestClient client) {
		client.get()
			.uri("/")
			.headers((header) -> header.setBasicAuth("user", "password"))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Hello, user!"));
	}

	@Test
	void friendlyShouldShowGivenName(WebTestClient client) {
		client.get()
			.uri("/friendly")
			.headers((header) -> header.setBasicAuth("user", "password"))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
				.isEqualTo("Hello, Dianne Emu!"));
	}

}
