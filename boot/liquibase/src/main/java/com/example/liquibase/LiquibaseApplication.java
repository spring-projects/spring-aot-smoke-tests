package com.example.liquibase;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(LiquibaseApplication.LiquibaseRuntimeHints.class)
public class LiquibaseApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LiquibaseApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class LiquibaseRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerPattern("db/changelog/changelogs/*.yaml");
		}

	}

}
