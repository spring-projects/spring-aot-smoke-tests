package com.example.servlet.jetty;

import org.springframework.aot.smoketest.thirdpartyhints.JettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(JettyRuntimeHints.class)
public class ServletJettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletJettyApplication.class, args);
	}

}
