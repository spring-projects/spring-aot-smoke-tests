package com.example.webflux.jetty;

import com.example.webflux.jetty.dto.RecordDto;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/x")
	public String greet2() {
		return "hix!";
	}

	@GetMapping("/hello")
	public Mono<String> hello() {
		return Mono.just("World");
	}

	@GetMapping("/record")
	public Mono<RecordDto> record() {
		return Mono.just(new RecordDto("Hello", "World"));
	}

}
