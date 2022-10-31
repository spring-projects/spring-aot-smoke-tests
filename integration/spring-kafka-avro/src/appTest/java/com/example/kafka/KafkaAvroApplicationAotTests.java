/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kafka;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class KafkaAvroApplicationAotTests {

	@Test
	void kafkaListenerMethodReceivesMessageAndSendsResponse(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(30)).untilAsserted(() -> assertThat(output)
				.hasSingleLineContaining("++++++1:Received Thing1:").hasSingleLineContaining("++++++2:Received Thing2:")
				.hasSingleLineContaining("++++++3:Received Thing3:").hasSingleLineContaining("++++++4:Received Thing4:")
				.hasSingleLineContaining("++++++5:Received Thing5:").hasSingleLineContaining("++++++6:Received Thing6:")
				.hasSingleLineContaining("++++++7:Received Thing7:")
				.hasSingleLineContaining("++++++8:Received Thing8:"));
	}

}
