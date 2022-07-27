package com.example.data.mongodb.reactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final PersonRepository personRepository;

	CLR(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public void run(String... args) {
		this.personRepository.save(new Person("first-1", "last-1")).block();
		this.personRepository.save(new Person("first-2", "last-2")).block();
		this.personRepository.save(new Person("first-3", "last-3")).block();

		this.personRepository.findAll().subscribe((person) -> {
			System.out.printf("findAll(): %s%n", person);
		}, ex -> ex.printStackTrace(System.out));

		this.personRepository.findByLastname("last-3").subscribe((person) -> {
			System.out.printf("findByLastname(): %s%n", person);
		}, ex -> ex.printStackTrace(System.out));
	}

}
