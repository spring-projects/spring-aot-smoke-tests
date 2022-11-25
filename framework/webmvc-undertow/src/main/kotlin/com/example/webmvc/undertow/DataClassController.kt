package com.example.webmvc.undertow

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/data-class", produces = [MediaType.APPLICATION_JSON_VALUE])
class DataClassController {
	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
	fun dataClass(@RequestBody greeting: Greeting): Greeting {
		return Greeting("Howdy!", greeting.name)
	}
}

data class Greeting(val greeting: String, val name: String)
