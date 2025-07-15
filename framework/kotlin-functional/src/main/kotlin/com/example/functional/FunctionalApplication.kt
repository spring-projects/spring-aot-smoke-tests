/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
	override fun run(vararg args: String) {
		println("Hello world!")
	}
}

