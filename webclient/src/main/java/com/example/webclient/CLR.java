package com.example.webclient;

import com.example.webclient.DataDto.DataDtoRuntimeHints;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Component
@ImportRuntimeHints(DataDtoRuntimeHints.class)
class CLR implements CommandLineRunner {

	private final Builder webClientBuilder;

	CLR(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	@Override
	public void run(String... args) {
		http();
		https();
	}

	private void http() {
		try {
			WebClient webClient = this.webClientBuilder.baseUrl("http://httpbin.org/").build();
			DataDto dto = webClient.get().uri("/anything").retrieve().bodyToMono(DataDto.class).block();
			System.out.printf("http: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("http failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void https() {
		try {
			WebClient webClient = this.webClientBuilder.baseUrl("https://httpbin.org/").build();
			DataDto dto = webClient.get().uri("/anything").retrieve().bodyToMono(DataDto.class).block();
			System.out.printf("https: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("https failed:");
			ex.printStackTrace(System.out);
		}
	}

}
