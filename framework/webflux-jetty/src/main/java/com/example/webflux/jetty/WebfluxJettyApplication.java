package com.example.webflux.jetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WebfluxJettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxJettyApplication.class, args);
	}

	@RestController
	class Foo {

		@GetMapping("/")
		public String greet() {
			return "hi!";
		}

	}

}
