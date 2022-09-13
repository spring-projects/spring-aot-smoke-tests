package com.example.servlet.undertow;

import org.springframework.aot.smoketest.thirdpartyhints.UndertowRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(UndertowRuntimeHints.class)
public class ServletUndertowApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServletUndertowApplication.class, args);
	}

}
