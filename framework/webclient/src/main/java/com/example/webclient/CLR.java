package com.example.webclient;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
class CLR implements CommandLineRunner {

	private final WebClient httpWebClient;

	private final WebClient httpsWebClient;

	private final DataService dataService;

	CLR(@Qualifier("http") WebClient httpWebClient, @Qualifier("https") WebClient httpsWebClient,
			DataService dataService) {
		this.httpWebClient = httpWebClient;
		this.httpsWebClient = httpsWebClient;
		this.dataService = dataService;
	}

	@Override
	public void run(String... args) {
		http();
		https();
		service();
	}

	private void http() {
		try {
			String response = this.httpWebClient.get()
				.uri("/")
				.retrieve()
				.bodyToMono(String.class)
				.timeout(Duration.ofSeconds(10))
				.block();
			System.out.printf("http worked: %s%n", response);
		}
		catch (Exception ex) {
			System.out.println("http failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void https() {
		try {
			String response = this.httpsWebClient.get()
				.uri("/")
				.retrieve()
				.bodyToMono(String.class)
				.timeout(Duration.ofSeconds(10))
				.block();
			System.out.printf("https worked: %s%n", response);
		}
		catch (Exception ex) {
			System.out.println("https failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void service() {
		try {
			String dto = this.dataService.getData();
			System.out.printf("service worked: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("service failed:");
			ex.printStackTrace(System.out);
		}
	}

}
