/*
 * Copyright 2022-2024 the original author or authors.
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

package com.example.pulsar;

import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.common.schema.SchemaType;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.core.PulsarTemplate;

@SpringBootApplication
public class SpringPulsarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPulsarApplication.class, args);
	}

	@Bean
	ApplicationRunner sendMessageToTopicOnAppStartup(PulsarTemplate<Greeting> pulsarTemplate) {
		String topic = "graalvm-demo-topic";
		return args -> pulsarTemplate.sendAsync(topic, new Greeting("Hello from GraalVM!"),
				Schema.JSON(Greeting.class));
	}

	@PulsarListener(subscriptionName = "graalvm-demo-subscription", topics = "graalvm-demo-topic",
			schemaType = SchemaType.JSON)
	void receiveMessageFromTopic(Greeting message) {
		System.out.println("Message Received: " + message);
	}

	record Greeting(String message) {
	}

}
