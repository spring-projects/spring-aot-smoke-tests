package com.example.cloud.config.client;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
public class ConfigClientApplicationAotTests {

	@Test
	void shouldRetrieveEnvironmentFromConfigServer(AssertableOutput output) {
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(output).hasLineContaining("Fetching config from server");
			assertThat(output)
				.hasLineContaining("Located environment: name=client-service, profiles=[default], label=null");
		});

	}

}
