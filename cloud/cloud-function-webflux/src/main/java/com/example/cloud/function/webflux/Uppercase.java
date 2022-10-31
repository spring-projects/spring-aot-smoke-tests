package com.example.cloud.function.webflux;

import java.util.Locale;
import java.util.function.Function;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

@Component
class Uppercase implements Function<Mono<String>, Mono<String>> {

	@Override
	public Mono<String> apply(Mono<String> input) {
		return input.map((i) -> i.toUpperCase(Locale.ENGLISH));
	}

}
