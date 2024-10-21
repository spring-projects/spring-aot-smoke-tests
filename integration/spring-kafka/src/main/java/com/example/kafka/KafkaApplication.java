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

package com.example.kafka;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@KafkaListener(id = "graal", topics = "graal")
	public void listen(Greeting in) {
		System.out.println("++++++Received: " + in);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("graal").partitions(1).replicas(1).build();
	}

	@Bean
	public ApplicationRunner runner(KafkaTemplate<Object, Object> template, ConsumerFactory<Object, Object> cf) {

		cf.addListener(new ConsumerFactory.Listener<>() {
		});
		return args -> {
			Greeting data = new Greeting("Hello from GraalVM!");
			template.send("graal", data);
			System.out.println("++++++Sent: " + data);
		};
	}

	public static class Greeting {

		private final String message;

		@JsonCreator
		public Greeting(@JsonProperty("message") String message) {
			this.message = message;
		}

		public String getMessage() {
			return this.message;
		}

		@Override
		public String toString() {
			return "Greeting{" + "message='" + this.message + '\'' + '}';
		}

	}

}
