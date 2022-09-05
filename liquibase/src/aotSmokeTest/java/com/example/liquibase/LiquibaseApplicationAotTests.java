package com.example.liquibase;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class LiquibaseApplicationAotTests {

	@Test
	void liquibaseRan(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining(
					"ChangeSet classpath:/db/changelog/db.changelog-master.yaml::1::nvoxland ran successfully in ");
			assertThat(output).hasSingleLineContaining(
					"ChangeSet classpath:/db/changelog/db.changelog-master.yaml::2::nvoxland ran successfully in ");
			assertThat(output).hasSingleLineContaining(
					"ChangeSet classpath:/db/changelog/db.changelog-master.yaml::3::nvoxland ran successfully in ");
		});
	}

}
