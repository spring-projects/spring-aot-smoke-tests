package com.example.security.method;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class PreAuthorizeAotTests {

	@Test
	void anonymousCanCallOnlyAnonymousMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining(
							"testAnonymous(): preAuthorizeProtectedService.anonymous() worked as anonymous")
					.hasSingleLineContaining("testUser(): preAuthorizeProtectedService.user() failed as anonymous")
					.hasSingleLineContaining("testAdmin(): preAuthorizeProtectedService.admin() failed as anonymous");
		});
	}

	@Test
	void userCanCallUserMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("testUser(): preAuthorizeProtectedService.user() worked as user")
					.hasSingleLineContaining("testAdmin(): preAuthorizeProtectedService.admin() failed as user");
		});
	}

	@Test
	void adminCanCallAdminMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("testAdmin(): preAuthorizeProtectedService.admin() worked as admin");
		});
	}

}
