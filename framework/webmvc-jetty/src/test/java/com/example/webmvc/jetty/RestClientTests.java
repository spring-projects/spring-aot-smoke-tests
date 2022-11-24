package com.example.webmvc.jetty;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
class RestClientTests {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private MockRestServiceServer mockServer;

	@Test
	void test() throws IOException {
		RestTemplate restTemplate = this.restTemplateBuilder.build();
		this.mockServer.expect(requestTo("/")).andRespond(withSuccess("hello", MediaType.TEXT_PLAIN));
		ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo("hello");
	}

}
