package com.example.tcf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class MyServiceConfiguration {

	@Bean
	MyService myService() {
		return new MyServiceImpl();
	}

}
