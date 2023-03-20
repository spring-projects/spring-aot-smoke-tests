package com.example.security.method;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class SecuredAotTests {

	@Test
	void anonymousCanCallOnlyAnonymousMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining(
						"testSecuredAnonymous(): securedProtectedService.anonymous() worked as anonymous")
				.hasSingleLineContaining("testSecuredUser(): securedProtectedService.user() failed as anonymous")
				.hasSingleLineContaining("testSecuredAdmin(): securedProtectedService.admin() failed as anonymous");
		});
	}

	@Test
	void userCanCallUserMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining("testSecuredUser(): securedProtectedService.user() worked as user")
				.hasSingleLineContaining("testSecuredAdmin(): securedProtectedService.admin() failed as user");
		});
	}

	@Test
	void adminCanCallAdminMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining("testSecuredAdmin(): securedProtectedService.admin() worked as admin");
		});
	}

}
