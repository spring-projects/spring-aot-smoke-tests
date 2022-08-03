package com.example.security.thymeleaf;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
public class SecurityThymeleafApplicationAotTests {

	@Test
	void helloShouldBeProtectedWithNoCredentials(WebTestClient client) {
		client.get().uri("/hello").exchange().expectStatus().isUnauthorized();
	}

	@Test
	void homeShouldNotBeProtectedWithNoCredentials(WebTestClient client) {
		client.get().uri("/").exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.contains("Click <a href=\"/hello\">here</a> to see a greeting."));
	}

	@Test
	void helloShouldShowUsernameWithRoleUser(WebTestClient client) {
		client.get().uri("/hello").headers((header) -> header.setBasicAuth("user", "password")).exchange()
				.expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.contains("Hello <span>user</span>").contains("Logged in user: <span>user</span>")
						.contains("Roles: <span>[ROLE_USER]</span>"));
	}

	@Test
	void helloShouldNotShowUsernameWithRoleAdmin(WebTestClient client) {
		client.get().uri("/hello").headers((header) -> header.setBasicAuth("admin", "password")).exchange()
				.expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.doesNotContain("Hello <span>admin</span>").contains("Logged in user: <span>admin</span>")
						.contains("Roles: <span>[ROLE_ADMIN]</span>"));
	}

}
