package com.example.data.redis;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.collections.DefaultRedisSet;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	final StringRedisTemplate template;

	private final PersonRepository personRepository;

	private final PubSubMessageHandler messageHandler;

	CLR(StringRedisTemplate template, PersonRepository personRepository, PubSubMessageHandler messageHandler) {
		this.template = template;
		this.personRepository = personRepository;
		this.messageHandler = messageHandler;
	}

	@Override
	public void run(String... args) throws Exception {

		connectionCommand();
		templateOperations();
		keyBoundOperations();
		redisBackedSet();
		hashMapper(HashStructure.DEFAULT);
		hashMapper(HashStructure.FLAT);
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

		for (PersonProjection person : this.personRepository.findProjectionByLastname("last-2")) {
			System.out.printf("findProjectionByLastname(): %s%n", person.getFirstname());
		}
	}

	private void jsonSerializer() {
		RedisTemplate<String, Person> t = new RedisTemplate<>();
		t.setConnectionFactory(this.template.getConnectionFactory());
		t.setKeySerializer(RedisSerializer.string());
		t.setValueSerializer(RedisSerializer.json());
		t.afterPropertiesSet();
		t.opsForValue().set("json-serializer", new Person("json-serialized-1", "value"));
		System.out.printf("json-serializer: %s%n", t.opsForValue().get("json-serializer"));
	}

	private void redisBackedSet() {
		RedisSet<String> redisSet = new DefaultRedisSet<>("redis-set", this.template);
		redisSet.add("OK");
		System.out.printf("redis set: %s%n", redisSet.iterator().next());
	}

	private void hashMapper(HashStructure structure) {
		Jackson2HashMapper hashMapper = new Jackson2HashMapper(HashStructure.FLAT.equals(structure));
		this.template.opsForHash().putAll("hash", hashMapper.toHash(new Person("hashed-fn", "hashed-ln")));

		Map<String, Object> hashedEntry = this.template.<String, Object>opsForHash().entries("hash");
		System.out.printf("hash-mapper-%s-raw: %s%n", structure, hashedEntry);
		System.out.printf("hash-mapper-%s-mapped: %s%n", structure, hashMapper.fromHash(hashedEntry));
	}

	private void keyBoundOperations() {
		BoundValueOperations<String, String> keyBoundOps = this.template.boundValueOps("bound-key");
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

		MessageListenerAdapter adapter = new MessageListenerAdapter(this.messageHandler);
		adapter.setSerializer(this.template.getValueSerializer());
		adapter.afterPropertiesSet();

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(this.template.getConnectionFactory());
		container.setBeanName("container");
		container.addMessageListener(adapter, List.of(new ChannelTopic(channel)));
		container.afterPropertiesSet();
		container.start();

		this.template.convertAndSend(channel, "payload");

		Thread.sleep(100);
		System.out.printf("pub/sub: %s%n", this.messageHandler.receivedMessages());
	}

	enum HashStructure {

		DEFAULT, FLAT;

		@Override
		public String toString() {
			return name().toLowerCase();
		}

	}

}
