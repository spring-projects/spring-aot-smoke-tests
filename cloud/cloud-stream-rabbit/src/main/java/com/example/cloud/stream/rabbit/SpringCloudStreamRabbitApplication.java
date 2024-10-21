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

package com.example.cloud.stream.rabbit;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudStreamRabbitApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringCloudStreamRabbitApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	public Function<Word, String> graalUppercaseFunction() {
		return word -> {
			String s = new String("{\"size\":" + word.size + ",\"word\":\"" + word.getWord().toUpperCase() + "\"}");
			return s;
		};
	}

	@Bean
	public Consumer<Word> graalLoggingConsumer(StreamBridge streamBridge) {
		return s -> {
			System.out.println("++++++Received:" + s);
			// Verifying that StreamBridge API works in native applications.
			streamBridge.send("sb-out", s);
		};
	}

	@Bean
	public Supplier<Word> graalSupplier() {
		final String[] splitWoodchuck = "How much wood could a woodchuck chuck if a woodchuck could chuck wood?"
			.split(" ");
		final AtomicInteger wordsIndex = new AtomicInteger(0);
		return () -> {
			int wordIndex = wordsIndex.getAndAccumulate(splitWoodchuck.length,
					(curIndex, numWords) -> curIndex < numWords - 1 ? curIndex + 1 : 0);
			String strWord = splitWoodchuck[wordIndex];
			Word word = new Word(strWord.length(), strWord);
			return word;
		};
	}

	public static class Word {

		private int size;

		private String word;

		public Word() {

		}

		public Word(int size, String word) {
			this.size = size;
			this.word = word;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getSize() {
			return this.size;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public String getWord() {
			return this.word;
		}

		public String toString() {
			return this.word + ":" + this.size;
		}

	}

}
