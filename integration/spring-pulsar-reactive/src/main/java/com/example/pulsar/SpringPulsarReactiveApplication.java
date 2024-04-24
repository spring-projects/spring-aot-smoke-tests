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
