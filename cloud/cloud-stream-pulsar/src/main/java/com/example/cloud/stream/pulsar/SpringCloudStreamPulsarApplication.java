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
