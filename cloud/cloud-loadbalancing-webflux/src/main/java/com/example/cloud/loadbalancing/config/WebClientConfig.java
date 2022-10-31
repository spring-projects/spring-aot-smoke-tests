package com.example.cloud.loadbalancing.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(name = "test-service", configuration = CustomLoadBalancerConfiguration.class)
public class WebClientConfig {

	@LoadBalanced
	@Bean
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

}
