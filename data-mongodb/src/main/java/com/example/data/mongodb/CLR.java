package com.example.data.mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final PersonRepository personRepository;

	CLR(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public void run(String... args) throws Exception {
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

}
