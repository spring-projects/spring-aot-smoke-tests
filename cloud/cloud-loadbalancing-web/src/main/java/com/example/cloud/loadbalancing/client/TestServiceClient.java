package com.example.cloud.loadbalancing.client;

import java.net.URI;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TestServiceClient {

	private final RestTemplate restTemplate;

	public TestServiceClient(@LoadBalanced RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String test() {
		return restTemplate.getForObject(URI.create("http://test-service"), String.class);
	}

}
