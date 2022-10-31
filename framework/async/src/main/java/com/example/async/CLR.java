package com.example.async;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	public static final AtomicReference<String> PREFIX = new AtomicReference<>("missing:");

	private final Runner runner;

	public CLR(Runner runner) {
		this.runner = runner;
	}

	@Override
	public void run(String... args) throws Exception {
		runner.run();
		PREFIX.set("set:");
	}

}
