package com.example.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class WebClientApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication application = new SpringApplication(WebClientApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	DataService dataService(WebClient.Builder webClientBuilder) {
		WebClient webClient = webClientBuilder.baseUrl("https://httpbin.org/").build();
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
				.build();
		return factory.createClient(DataService.class);
	}

}
