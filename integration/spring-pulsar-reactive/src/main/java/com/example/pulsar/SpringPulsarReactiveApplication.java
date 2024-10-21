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

import java.time.Duration;

import org.apache.pulsar.reactive.client.api.MessageSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.pulsar.reactive.config.annotation.ReactivePulsarListener;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;

@SpringBootApplication
public class SpringPulsarReactiveApplication {

	private static final Logger LOG = LoggerFactory.getLogger(SpringPulsarReactiveApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringPulsarReactiveApplication.class, args);
	}

	@Bean
	ApplicationRunner sendMessageToTopicOnAppStartup(ReactivePulsarTemplate<String> reactivePulsarTemplate) {
		String topic = "graalvm-demo-topic-reactive";
		return args -> Flux.range(0, 10)
			.map((i) -> MessageSpec.of("sample-message-" + i))
			.as((msgs) -> reactivePulsarTemplate.send(topic, msgs))
			.delaySubscription(Duration.ofSeconds(3L))
			.subscribe();
	}

	@ReactivePulsarListener(subscriptionName = "graalvm-demo-subscription-reactive",
			topics = "graalvm-demo-topic-reactive")
	public Mono<Void> listenReactive(String message) {
		LOG.info("Message Received: " + message);
		return Mono.empty();
	}

}
