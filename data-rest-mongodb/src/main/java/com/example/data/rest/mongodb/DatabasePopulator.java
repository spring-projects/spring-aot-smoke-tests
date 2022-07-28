package com.example.data.rest.mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class DatabasePopulator implements CommandLineRunner {

	private final PersonRepository personRepository;

	DatabasePopulator(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public void run(String... args) {
		this.personRepository.save(new Person("first-1", "last-1"));
		this.personRepository.save(new Person("first-2", "last-2"));
		this.personRepository.save(new Person("first-3", "last-3"));
	}

}
