package com.example.webmvc.undertow.tls;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
	public String index() {
		return "Hello World TLS";
	}

}
