package com.example.webflux.undertow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WebfluxUndertowApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxUndertowApplication.class, args);
	}

	@RestController
	class Foo {

		@GetMapping("/")
		public String greet() {
			return "hi!";
		}

	}

}
