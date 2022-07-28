package com.example.security.method;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class SecurityMethodApplicationAotTests {

	@Test
	void anonymousCanCallOnlyAnonymousMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("testAnonymous(): protectedService.anonymous() worked as anonymous")
					.hasSingleLineContaining("testUser(): protectedService.user() failed as anonymous")
					.hasSingleLineContaining("testAdmin(): protectedService.admin() failed as anonymous");
		});
	}

	@Test
	void userCanCallUserMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("testUser(): protectedService.user() worked as user")
					.hasSingleLineContaining("testAdmin(): protectedService.admin() failed as user");
		});
	}

	@Test
	void adminCanCallAdminMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("testAdmin(): protectedService.admin() worked as admin");
		});
	}

}
