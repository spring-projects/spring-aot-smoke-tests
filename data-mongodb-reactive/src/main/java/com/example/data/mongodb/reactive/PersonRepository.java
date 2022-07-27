package com.example.data.mongodb.reactive;

import reactor.core.publisher.Flux;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person, String> {

	Flux<Person> findByLastname(String lastname);

}
