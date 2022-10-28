package com.example.security.method;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class Jsr250AotTests {

	@Test
	void anonymousCanCallOnlyAnonymousMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining(
							"testJsr250Anonymous(): jsr250ProtectedService.anonymous() worked as anonymous")
					.hasSingleLineContaining("testJsr250User(): jsr250ProtectedService.user() failed as anonymous")
					.hasSingleLineContaining("testJsr250Admin(): jsr250ProtectedService.admin() failed as anonymous")
					.hasSingleLineContaining(
							"testJsr250PermitAll(): jsr250ProtectedService.permitAll() worked as anonymous")
					.hasSingleLineContaining(
							"testJsr250DenyAll(): jsr250ProtectedService.denyAll() failed as anonymous");
		});
	}

	@Test
	void userCanCallUserMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("testJsr250User(): jsr250ProtectedService.user() worked as user")
					.hasSingleLineContaining("testJsr250Admin(): jsr250ProtectedService.admin() failed as user")
					.hasSingleLineContaining("testJsr250PermitAll(): jsr250ProtectedService.permitAll() worked as user")
					.hasSingleLineContaining("testJsr250DenyAll(): jsr250ProtectedService.denyAll() failed as user");
		});
	}

	@Test
	void adminCanCallAdminMethod(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
					.hasSingleLineContaining("testJsr250Admin(): jsr250ProtectedService.admin() worked as admin")
					.hasSingleLineContaining("testJsr250DenyAll(): jsr250ProtectedService.denyAll() failed as admin")
					.hasSingleLineContaining(
							"testJsr250PermitAll(): jsr250ProtectedService.permitAll() worked as admin");
		});
	}

}
