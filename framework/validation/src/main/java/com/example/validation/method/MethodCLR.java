package com.example.validation.method;

import jakarta.validation.ConstraintViolationException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class MethodCLR implements CommandLineRunner {

	private final SomeService someService;

	MethodCLR(SomeService someService) {
		this.someService = someService;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf("this.someService.hello('world'): %s%n", this.someService.hello("world"));
		try {
			this.someService.hello("");
			System.out.println("this.someService.hello(''): Invocation worked, this should not have happened!");
		}
		catch (ConstraintViolationException e) {
			System.out.println("this.someService.hello(''): Got expected exception");
		}
	}

}
