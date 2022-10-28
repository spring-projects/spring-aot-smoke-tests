package com.example.functional

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans

@SpringBootApplication
class FunctionalApplication

fun main(args: Array<String>) {
	runApplication<FunctionalApplication>(*args) {
		addInitializers(beans {
			bean<Foo>()
		})
	}
}

class Foo : CommandLineRunner {
	override fun run(vararg args: String?) {
		println("Hello world!")
	}
}

