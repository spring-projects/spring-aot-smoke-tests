package com.example.validation.properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(TestConfigurationProperties.class)
class PropertiesCLR implements CommandLineRunner {

	private final TestConfigurationProperties testConfigurationProperties;

	PropertiesCLR(TestConfigurationProperties testConfigurationProperties) {
		this.testConfigurationProperties = testConfigurationProperties;
	}

	@Override
	public void run(String... args) {
		System.out.printf("testConfigurationProperties.getField(): %s%n", testConfigurationProperties.getField());
		System.out.printf("testConfigurationProperties.getNested().getField(): %s%n",
				testConfigurationProperties.getNested().getField());
	}

}
