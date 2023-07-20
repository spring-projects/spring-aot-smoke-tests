package com.example.webclient;

import java.time.Duration;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RegisterReflectionForBinding(DataDto.class)
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
			DataDto dto = this.httpWebClient.get()
				.uri("/anything")
				.retrieve()
				.bodyToMono(DataDto.class)
				.timeout(Duration.ofSeconds(10))
				.block();
			System.out.printf("http: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("http failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void https() {
		try {
			DataDto dto = this.httpsWebClient.get()
				.uri("/anything")
				.retrieve()
				.bodyToMono(DataDto.class)
				.timeout(Duration.ofSeconds(10))
				.block();
			System.out.printf("https: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("https failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void service() {
		try {
			ExchangeDataDto dto = this.dataService.getData();
			System.out.printf("service: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("service failed:");
			ex.printStackTrace(System.out);
		}
	}

}
