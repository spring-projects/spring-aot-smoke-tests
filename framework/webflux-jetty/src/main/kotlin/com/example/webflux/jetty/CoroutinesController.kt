package com.example.webflux.jetty

import kotlinx.coroutines.delay
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.time.Duration.Companion.milliseconds

@RestController
@RequestMapping("/coroutine", produces = [MediaType.APPLICATION_JSON_VALUE])
class CoroutinesController {
	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
	suspend fun coroutines(@RequestBody greeting: Greeting): Greeting {
		delay(10.milliseconds)
		return Greeting("Howdy!", greeting.name)
	}
}
