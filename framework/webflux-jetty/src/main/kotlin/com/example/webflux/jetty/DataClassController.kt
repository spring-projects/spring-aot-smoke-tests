package com.example.webflux.jetty

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/data-class", produces = [MediaType.APPLICATION_JSON_VALUE])
class DataClassController {
	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
	fun dataClass(@RequestBody greeting: Mono<Greeting>): Mono<Greeting> {
		return greeting.map { Greeting("Howdy!", it.name) }
	}
}
