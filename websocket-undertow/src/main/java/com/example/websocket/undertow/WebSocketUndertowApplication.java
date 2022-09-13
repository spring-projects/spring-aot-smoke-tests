package com.example.websocket.undertow;

import org.springframework.aot.smoketest.thirdpartyhints.UndertowRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(UndertowRuntimeHints.class)
public class WebSocketUndertowApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketUndertowApplication.class, args);
	}

}
