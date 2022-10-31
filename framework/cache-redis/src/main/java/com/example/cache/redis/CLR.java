package com.example.cache.redis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final TestService testService;

	public CLR(TestService testService) {
		this.testService = testService;
	}

	@Override
	public void run(String... args) {
		this.testService.invoke();
		this.testService.invoke();
	}

}
