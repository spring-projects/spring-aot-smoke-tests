package com.example.configprops;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(AppProperties.class)
public class CLR implements CommandLineRunner {

	private final AppProperties appProperties;

	CLR(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf("appProperties.getString(): %s%n", appProperties.getString());
		System.out.printf("appProperties.getDataSize(): %s%n", appProperties.getDataSize());
		System.out.printf("appProperties.getStringList(): %s%n", appProperties.getStringList());
		System.out.printf("appProperties.getNestedList(): %s%n", appProperties.getNestedList());
		System.out.printf("appProperties.getNested(): %s%n", appProperties.getNested());
		System.out.printf("appProperties.getNested().getaInt(): %s%n", appProperties.getNested().getaInt());
	}

}
