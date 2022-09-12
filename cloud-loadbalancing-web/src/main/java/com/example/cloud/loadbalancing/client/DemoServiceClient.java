package com.example.cloud.loadbalancing.client;

import java.net.URI;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DemoServiceClient {

	private final RestTemplate restTemplate;

	public DemoServiceClient(@LoadBalanced RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String demo() {
		return restTemplate.getForObject(URI.create("http://demo-service"), String.class);
	}

}
