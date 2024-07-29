package com.example.functional

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class FunctionalApplication

fun main(args: Array<String>) {
	runApplication<FunctionalApplication>(*args) {
		addInitializers(beans {
			bean<Foo>()
			bean(::endpoints)
		})
	}
}

fun endpoints() = router {
	GET("/") { ServerResponse.ok().bodyValue("hi!") }
}

class Foo : CommandLineRunner {
	override fun run(vararg args: String?) {
		println("Hello world!")
	}
}

