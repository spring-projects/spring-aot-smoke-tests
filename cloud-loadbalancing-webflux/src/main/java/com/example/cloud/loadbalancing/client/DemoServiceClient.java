package com.example.cloud.loadbalancing.client;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DemoServiceClient {

	private final WebClient webClient;

	public DemoServiceClient(@LoadBalanced WebClient.Builder builder) {
		this.webClient = builder.build();
	}

	public Mono<String> demo() {
		return webClient.get().uri(URI.create("http://demo-service")).retrieve().bodyToMono(String.class);
	}

}
