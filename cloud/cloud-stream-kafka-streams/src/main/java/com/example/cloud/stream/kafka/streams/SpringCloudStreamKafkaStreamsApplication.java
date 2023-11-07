/*
 * Copyright 2023 the original author or authors.
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

package com.example.cloud.stream.kafka.streams;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudStreamKafkaStreamsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringCloudStreamKafkaStreamsApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	public Function<KStream<Bytes, String>, KStream<Bytes, String>> process() {

		return input -> input.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
			.map((key, value) -> new KeyValue<>(value, value))
			.groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
			.windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMillis(30_000)))
			.count(Materialized.as("WordCounts-1"))
			.toStream()
			.map((key, value) -> new KeyValue<>(null, key.key() + " : " + value));
	}

	@Bean
	public Consumer<String> graalLoggingConsumer() {
		return s -> {
			System.out.println("++++++Received:[" + s + "]");
		};
	}

	@Bean
	public Supplier<String> graalSupplier() {
		return () -> "woodchuck";
	}

}
