package com.example.resttemplate;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.requestFactory(SkipSslVerificationHttpRequestFactory::new)
			.defaultHeader("User-Agent", "RestTemplateApplication")
			.setConnectTimeout(Duration.ofSeconds(5))
			.setReadTimeout(Duration.ofSeconds(5))
			.build();
	}

}
