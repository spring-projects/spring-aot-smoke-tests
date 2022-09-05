package com.example.jdbc.postgresql;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class JdbcPostgreSQLApplicationAotTests {

	@Test
	void authorsCanBeQueried(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(
				() -> assertThat(output).hasSingleLineContaining("Found author: Author[id=1, name=mbhave]")
						.hasSingleLineContaining("Found author: Author[id=2, name=snicoll]")
						.hasSingleLineContaining("Found author: Author[id=3, name=wilkinsona]"));
	}

}
