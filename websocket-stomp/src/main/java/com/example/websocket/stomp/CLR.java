package com.example.websocket.stomp;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import com.example.websocket.stomp.dto.GreetingMessage;
import com.example.websocket.stomp.dto.HelloMessage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
class CLR implements CommandLineRunner {

	private final WebSocketStompClient webSocketStompClient;

	private final ServletWebServerApplicationContext servletWebServerApplicationContext;

	CLR(WebSocketStompClient webSocketStompClient,
			ServletWebServerApplicationContext servletWebServerApplicationContext) {
		this.webSocketStompClient = webSocketStompClient;
		this.servletWebServerApplicationContext = servletWebServerApplicationContext;
	}

	@Override
	public void run(String... args) throws Exception {
		MyStompHandler handler = new MyStompHandler();
		StompSession stompSession = this.webSocketStompClient
				.connect("ws://localhost:%d/stomp".formatted(getServerPort()), handler).get(5, TimeUnit.SECONDS);

		stompSession.subscribe("/topic/greetings", handler);
		stompSession.send("/app/hello", new HelloMessage("STOMP Client"));
	}

	private int getServerPort() {
		return this.servletWebServerApplicationContext.getWebServer().getPort();
	}

	private static class MyStompHandler extends StompSessionHandlerAdapter {

		@Override
		public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
			System.out.println("STOMP Client connected");
		}

		@Override
		public Type getPayloadType(StompHeaders headers) {
			return GreetingMessage.class;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			System.out.printf("Client: Received '%s'%n", payload);
		}

	}

}
