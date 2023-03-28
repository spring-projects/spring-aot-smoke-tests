package com.example.cloud.stream.pulsar;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudStreamPulsarApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringCloudStreamPulsarApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	Supplier<Word> graalSupplier() {
		var splitWoodchuck = "How much wood could a woodchuck chuck if a woodchuck could chuck wood?".split(" ");
		final var wordsIndex = new AtomicInteger(0);
		return () -> {
			var wordIndex = wordsIndex.getAndAccumulate(splitWoodchuck.length,
					(curIndex, numWords) -> curIndex < numWords - 1 ? curIndex + 1 : 0);
			var strWord = splitWoodchuck[wordIndex];
			return new Word(strWord.length(), strWord);
		};
	}

	@Bean
	Function<Word, String> graalUppercaseFunction() {
		return word -> "{\"size\":%d,\"word\":\"%s\"}".formatted(word.size(), word.word().toUpperCase());
	}

	@Bean
	public Consumer<Word> graalLoggingConsumer(StreamBridge streamBridge) {
		return word -> {
			System.out.println("++++++Received:" + word.word());
			// Verifying that StreamBridge API works in native applications.
			streamBridge.send("sb-out", word);
		};
	}

	public record Word(int size, String word) {
	}

}
