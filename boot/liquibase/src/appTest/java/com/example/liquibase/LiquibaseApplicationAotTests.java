package com.example.liquibase;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class LiquibaseApplicationAotTests {

	@Test
	void liquibaseRan(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output)
				.hasSingleLineContaining("ChangeSet db/changelog/changelogs/1.yaml::1::nvoxland ran successfully in");
			assertThat(output)
				.hasSingleLineContaining("ChangeSet db/changelog/changelogs/2.yaml::2::nvoxland ran successfully in");
			assertThat(output)
				.hasSingleLineContaining("ChangeSet db/changelog/changelogs/3.yaml::3::nvoxland ran successfully in");
		});
	}

}
