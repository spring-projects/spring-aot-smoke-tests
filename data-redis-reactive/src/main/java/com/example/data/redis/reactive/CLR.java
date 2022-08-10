package com.example.data.redis.reactive;

import reactor.core.publisher.Flux;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final ReactiveRedisOperations<String, Person> redisOperations;

	CLR(ReactiveRedisOperations<String, Person> redisOperations) {
		this.redisOperations = redisOperations;
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.just(new Person("1", "first-1", "last-1"), new Person("2", "first-2", "last-2"),
				new Person("3", "first-3", "last-3"))
				.flatMap((person) -> this.redisOperations.opsForValue().set(person.getId(), person)).collectList()
				.block();

		for (int i = 1; i <= 3; i++) {
			String id = Integer.toString(i);
			this.redisOperations.opsForValue().get(id).subscribe((person) -> System.out.printf("%s: %s%n", id, person),
					(ex) -> ex.printStackTrace(System.out));
		}
	}

}
