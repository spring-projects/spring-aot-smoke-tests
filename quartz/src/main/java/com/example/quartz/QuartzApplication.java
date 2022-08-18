package com.example.quartz;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.smoketest.thirdpartyhints.HikariRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints({ HikariRuntimeHints.class, QuartzApplication.Hints.class })
public class QuartzApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(QuartzApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class Hints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.reflection().registerType(SimpleJob.class,
					builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
							MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS));
		}

	}

}
