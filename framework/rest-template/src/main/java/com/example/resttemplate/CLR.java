package com.example.resttemplate;

import java.net.URI;
import java.time.Duration;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RegisterReflectionForBinding(DataDto.class)
class CLR implements CommandLineRunner {

	private final RestTemplateBuilder restTemplateBuilder;

	CLR(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplateBuilder = restTemplateBuilder;
	}

	@Override
	public void run(String... args) {
		RestTemplate restTemplate = this.restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(5))
				.setReadTimeout(Duration.ofSeconds(5)).build();
		http(restTemplate);
		https(restTemplate);
	}

	private void http(RestTemplate restTemplate) {
		try {
			DataDto dto = restTemplate.getForObject(URI.create("http://httpbin.org/anything"), DataDto.class);
			System.out.printf("http: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("http failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void https(RestTemplate restTemplate) {
		try {
			DataDto dto = restTemplate.getForObject(URI.create("https://httpbin.org/anything"), DataDto.class);
			System.out.printf("https: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("https failed:");
			ex.printStackTrace(System.out);
		}
	}

}
