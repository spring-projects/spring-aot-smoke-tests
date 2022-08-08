package com.example.cloud.stream.rabbit;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudStreamRabbitApplication {

	@Autowired
	private StreamBridge streamBridge;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringCloudStreamRabbitApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	public Function<String, String> graalUppercaseFunction() {
		return String::toUpperCase;
	}

	@Bean
	public Consumer<String> graalLoggingConsumer() {
		return s -> {
			System.out.println("++++++Received:" + s);
			// Verifying that StreamBridge API works in native applications.
			streamBridge.send("sb-out", s);
		};
	}

	@Bean
	public Supplier<String> graalSupplier() {
		final String[] splitWoodchuck = "How much wood could a woodchuck chuck if a woodchuck could chuck wood?".split(" ");
		final AtomicInteger wordsIndex = new AtomicInteger(0);
		return () -> {
			int wordIndex = wordsIndex.getAndAccumulate(splitWoodchuck.length, (curIndex, numWords) -> curIndex < numWords-1 ? curIndex+1 : 0);
			return splitWoodchuck[wordIndex];
		};
	}
}
