package com.example.cloud.openfeign.config;

import feign.Logger;

import org.springframework.context.annotation.Bean;

public class CustomFeignConfig {

	@Bean
	Logger.Level loggingLevel() {
		return Logger.Level.FULL;
	}

}
