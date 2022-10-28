package com.example.cloud.stream.rabbit;
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



import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class SpringCloudStreamRabbitApplicationAotTests {

	@Test
	void suppliedMessageIsUppercasedAndLogged(AssertableOutput output) {
		// INPUT -> How much wood could a woodchuck chuck if a woodchuck could chuck
		// wood?"
		Awaitility.await().atMost(Duration.ofSeconds(30))
				.untilAsserted(() -> assertThat(output).hasLineContaining("++++++Received:HOW")
						.hasLineContaining("++++++Received:MUCH").hasLineContaining("++++++Received:WOOD")
						.hasLineContaining("++++++Received:COULD").hasLineContaining("++++++Received:A")
						.hasLineContaining("++++++Received:WOODCHUCK").hasLineContaining("++++++Received:CHUCK")
						.hasLineContaining("++++++Received:IF").hasLineContaining("++++++Received:COULD")
						.hasLineContaining("++++++Received:WOOD?"));
	}

}
