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
