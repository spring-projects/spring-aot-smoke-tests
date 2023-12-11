package com.example.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ResourceConfiguration {

	@Bean
	String hello() {
		return "Hello";
	}

	@Bean
	String test() {
		return "Test";
	}

	@Bean
	Integer counter() {
		return 42;
	}

}
