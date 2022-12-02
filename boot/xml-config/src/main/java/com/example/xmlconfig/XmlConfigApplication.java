package com.example.xmlconfig;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XmlConfigApplication {

	private static final String CONTEXT_XML = "classpath:/application-context.xml";

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication();
		application.setSources(Collections.singleton(CONTEXT_XML));
		application.run(args);
	}

}
