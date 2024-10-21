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

package com.example.data.redis.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisCallback;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
class CLR implements CommandLineRunner {

	@Autowired
	private ReactiveStringRedisTemplate template;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ReactiveRedisConnectionFactory connectionFactory;

	@Override
	public void run(String... args) throws Exception {

		connectionCommand();
		templateOperations();
		jsonSerializer();
		pubSub();
	}

	private void jsonSerializer() {

		Jackson2JsonRedisSerializer<Person> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Person.class);
		RedisSerializationContext.RedisSerializationContextBuilder<String, Person> builder = RedisSerializationContext
			.newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<String, Person> context = builder.value(serializer).build();
		ReactiveRedisTemplate<String, Person> personTemplate = new ReactiveRedisTemplate<>(connectionFactory, context);

		Flux.just(new Person("1", "first-1", "last-1"), new Person("2", "first-2", "last-2"),
				new Person("3", "first-3", "last-3"))
			.flatMap((person) -> personTemplate.opsForValue().set(person.getId(), person))
			.collectList()
			.block();

		for (int i = 1; i <= 3; i++) {
			String id = Integer.toString(i);
			personTemplate.opsForValue()
				.get(id)
				.subscribe((person) -> System.out.printf("%s: %s%n", id, person),
						(ex) -> ex.printStackTrace(System.out));
		}
	}

	private void templateOperations() {
		this.template.opsForValue().set("success-token", "OK").block();
		this.template.opsForValue()
			.get("success-token")
			.subscribe(value -> System.out.printf("template ops: %s%n", value), (ex) -> ex.printStackTrace(System.out));
	}

	private void connectionCommand() {
		this.template.execute((ReactiveRedisCallback<String>) (connection) -> {
			return connection.serverCommands().flushAll().flatMap((it) -> {
				System.out.println("connection: OK");
				return Mono.just("OK");
			});
		}).blockFirst();
	}

	private void pubSub() throws InterruptedException {

		String channel = "pubsub::test";
		Disposable messageListener = template.listenToChannel(channel).map(it -> {
			System.out.printf("pub/sub: %s%n", it.getMessage());
			return it;
		}).subscribe();

		Thread.sleep(100);

		template.convertAndSend(channel, "payload").block();

		Thread.sleep(100);
		messageListener.dispose();
	}

}
