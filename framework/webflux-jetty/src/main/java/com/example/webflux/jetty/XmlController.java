package com.example.webflux.jetty;

import com.example.webflux.jetty.dto.Request;
import com.example.webflux.jetty.dto.Response;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
public class XmlController {

	@GetMapping
	public Mono<Response> response() {
		return Mono.just(new Response("Hello XML"));
	}

	@PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
	public Mono<Response> echo(@RequestBody Mono<Request> request) {
		return request.map((r) -> new Response("Server: " + r.getMessage()));
	}

}
