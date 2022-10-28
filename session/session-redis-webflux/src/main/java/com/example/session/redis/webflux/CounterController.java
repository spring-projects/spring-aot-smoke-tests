package com.example.session.redis.webflux;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

@RestController
@RequestMapping(path = "/counter", produces = MediaType.APPLICATION_JSON_VALUE)
public class CounterController {

	@GetMapping
	public Mono<Map<String, Integer>> index(WebSession session) {
		int counter = session.getAttributeOrDefault("counter", 1);
		session.getAttributes().put("counter", counter + 1);
		return Mono.just(Map.of("counter", counter));
	}

}
