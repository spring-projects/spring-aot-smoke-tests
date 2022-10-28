package com.example.webflux.tls;

import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
	public Mono<String> greet() {
		return Mono.just("Hello World TLS");
	}

}
