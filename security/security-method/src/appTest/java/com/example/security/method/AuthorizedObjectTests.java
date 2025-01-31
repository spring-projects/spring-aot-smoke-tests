package com.example.security.method;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class AuthorizedObjectTests {

	@Test
	void anonymousCanOnlyCallPermittedMethods(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> assertThat(output)
		// @formatter:off
				.hasSingleLineContaining("testAuthorizedObject(): object.getUserProperty() correctly allowed as anonymous")
				.hasSingleLineContaining("testAuthorizedObject(): object.getAdminProperty() correctly allowed as anonymous")
				.hasSingleLineContaining("testAuthorizedObject(): object.getUserPropertyJsr250() correctly allowed as anonymous")
				.hasSingleLineContaining("testAuthorizedObject(): object.getUserPropertySecured() correctly allowed as anonymous")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserProperty() correctly denied as anonymous")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getAdminProperty() correctly denied as anonymous")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserPropertyJsr250() correctly denied as anonymous")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserPropertySecured() correctly denied as anonymous"));
		// @formatter:on
	}

	@Test
	void userCanOnlyCallPermittedMethods(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> assertThat(output)
		// @formatter:off
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserProperty() correctly allowed as user")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getAdminProperty() correctly denied as user")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserPropertyJsr250() correctly allowed as user")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserPropertySecured() correctly allowed as user"));
		// @formatter:on
	}

	@Test
	void adminCanOnlyCallPermittedMethods(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> assertThat(output)
		// @formatter:off
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserProperty() correctly denied as admin")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getAdminProperty() correctly allowed as admin")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserPropertyJsr250() correctly denied as admin")
				.hasSingleLineContaining("testAuthorizedObject(): authorized.getUserPropertySecured() correctly denied as admin"));
		// @formatter:on
	}

}
