package com.example.security.method;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class PostAuthorizeAotTests {

	@Test
	void anonymousCanCallOnlyAnonymousMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining(
						"testPostAuthorizeAnonymous(): postAuthorizeProtectedService.anonymous() worked as anonymous")
				.hasSingleLineContaining(
						"testPostAuthorizeUser(): postAuthorizeProtectedService.user() failed as anonymous")
				.hasSingleLineContaining(
						"testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() failed as anonymous");
		});
	}

	@Test
	void userCanCallUserMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining("testPostAuthorizeUser(): postAuthorizeProtectedService.user() worked as user")
				.hasSingleLineContaining(
						"testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() failed as user");
		});
	}

	@Test
	void adminCanCallAdminMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining(
					"testPostAuthorizeAdmin(): postAuthorizeProtectedService.admin() worked as admin");
		});
	}

}
