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

package com.example.data.redis;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.collections.DefaultRedisSet;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	@Autowired
	StringRedisTemplate template;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PubSubMessageHandler messageHandler;

	@Override
	public void run(String... args) throws Exception {

		connectionCommand();
		templateOperations();
		keyBoundOperations();
		redisBackedSet();
		jsonSerializer();
		pubSub();

		this.personRepository.save(new Person("first-1", "last-1"));
		this.personRepository.save(new Person("first-2", "last-2"));
		this.personRepository.save(new Person("first-3", "last-3"));

		for (Person person : this.personRepository.findAll()) {
			System.out.printf("findAll(): %s%n", person);
		}

		for (Person person : this.personRepository.findByLastname("last-3")) {
			System.out.printf("findByLastname(): %s%n", person);
		}
	}

	private void jsonSerializer() {
		RedisTemplate<String, Person> t = new RedisTemplate<>();
		t.setConnectionFactory(template.getConnectionFactory());
		t.setKeySerializer(RedisSerializer.string());
		t.setValueSerializer(RedisSerializer.json());
		t.afterPropertiesSet();
		t.opsForValue().set("json-serializer", new Person("json-serialized-1", "value"));
		System.out.printf("json-serializer: %s%n", t.opsForValue().get("json-serializer"));
	}

	private void redisBackedSet() {
		RedisSet<String> redisSet = new DefaultRedisSet<>("redis-set", template);
		redisSet.add("OK");
		System.out.printf("redis set: %s%n", redisSet.iterator().next());
	}

	private void keyBoundOperations() {
		BoundValueOperations<String, String> keyBoundOps = template.boundValueOps("bound-key");
		keyBoundOps.set("OK");
		System.out.printf("key bound ops: %s%n", keyBoundOps.get());
	}

	private void templateOperations() {
		this.template.opsForValue().set("success-token", "OK");
		System.out.printf("template ops: %s%n", this.template.opsForValue().get("success-token"));
	}

	private void connectionCommand() {
		this.template.execute((RedisCallback<String>) (connection) -> {
			connection.serverCommands().flushAll();
			System.out.println("connection: OK");
			return "OK";
		});
	}

	private void pubSub() throws InterruptedException {

		String channel = "pubsub::test";

		MessageListenerAdapter adapter = new MessageListenerAdapter(messageHandler);
		adapter.setSerializer(template.getValueSerializer());
		adapter.afterPropertiesSet();

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(template.getConnectionFactory());
		container.setBeanName("container");
		container.addMessageListener(adapter, Arrays.asList(new ChannelTopic(channel)));
		container.afterPropertiesSet();
		container.start();

		template.convertAndSend(channel, "payload");

		Thread.sleep(100);
		System.out.printf("pub/sub: %s%n", messageHandler.receivedMessages());
	}

}
