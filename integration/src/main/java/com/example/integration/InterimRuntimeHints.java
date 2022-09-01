package com.example.integration;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class InterimRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection().registerType(TypeReference.of("org.springframework.data.redis.core.DefaultListOperations"),
				hint -> hint.withMembers(MemberCategory.INVOKE_PUBLIC_METHODS));
	}

}
