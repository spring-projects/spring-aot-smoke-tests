package com.example.configprops;

import com.example.configprops.ConfigPropsApplication.ConfigPropsApplicationRuntimeHints;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(ConfigPropsApplicationRuntimeHints.class)
public class ConfigPropsApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ConfigPropsApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class ConfigPropsApplicationRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerPattern("test.yaml");
		}

	}

}
