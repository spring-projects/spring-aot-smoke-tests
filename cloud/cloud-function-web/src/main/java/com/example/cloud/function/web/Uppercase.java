package com.example.cloud.function.web;

import java.util.Locale;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
class Uppercase implements Function<String, String> {

	@Override
	public String apply(String input) {
		return input.toUpperCase(Locale.ENGLISH);
	}

}
