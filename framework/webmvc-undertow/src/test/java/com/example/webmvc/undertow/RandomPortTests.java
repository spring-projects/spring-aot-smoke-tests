package com.example.webmvc.undertow;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RandomPortTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Test
	void check() {
		assertThat(this.port).isNotZero();
	}

	@Test
	void testRestTemplateWorks() {
		ResponseEntity<String> response = this.template.getForEntity("/", String.class);
		assertThat(response.getBody()).isEqualTo("Hello from Spring MVC and Undertow");
	}

}
