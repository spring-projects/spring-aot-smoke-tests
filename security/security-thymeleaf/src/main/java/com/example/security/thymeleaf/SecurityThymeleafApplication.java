package com.example.security.thymeleaf;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
@ImportRuntimeHints(SecurityThymeleafApplication.Hints.class)
public class SecurityThymeleafApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(SecurityThymeleafApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class Hints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.reflection().registerType(UsernamePasswordAuthenticationToken.class,
					MemberCategory.INVOKE_PUBLIC_METHODS);
			hints.reflection().registerType(User.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		}

	}

}
