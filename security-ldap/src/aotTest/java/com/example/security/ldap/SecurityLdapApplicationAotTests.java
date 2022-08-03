package com.example.security.ldap;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
public class SecurityLdapApplicationAotTests {

	@Test
	void anonymousShouldBeUnauthorizedWithoutCredentials(WebTestClient client) {
		client.get().uri("/").exchange().expectStatus().isUnauthorized();
	}

	@Test
	void homeShouldShowUsername(WebTestClient client) {
		client.get().uri("/").headers((header) -> header.setBasicAuth("user", "password")).exchange().expectStatus()
				.isOk().expectBody().consumeWith(
						(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Hello, user!"));
	}

	@Test
	void friendlyShouldShowGivenName(WebTestClient client) {
		client.get().uri("/friendly").headers((header) -> header.setBasicAuth("user", "password")).exchange()
				.expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.isEqualTo("Hello, Dianne Emu!"));
	}

}
