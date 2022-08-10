package com.example.data.redis.reactive;

import com.example.data.redis.reactive.DataRedisReactiveApplication.PersonRuntimeHints;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.aot.BindingReflectionHintsRegistrar;

@SpringBootApplication
@ImportRuntimeHints({ NettyRuntimeHints.class, PersonRuntimeHints.class })
public class DataRedisReactiveApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataRedisReactiveApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class PersonRuntimeHints implements RuntimeHintsRegistrar {

		private final BindingReflectionHintsRegistrar bindingReflectionHintsRegistrar = new BindingReflectionHintsRegistrar();

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			this.bindingReflectionHintsRegistrar.registerReflectionHints(hints.reflection(), Person.class);
		}

	}

}
