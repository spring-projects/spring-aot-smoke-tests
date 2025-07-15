/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.websocket.stomp;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import com.example.websocket.stomp.dto.GreetingMessage;
import com.example.websocket.stomp.dto.HelloMessage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
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
			.connectAsync("ws://localhost:%d/stomp".formatted(getServerPort()), handler)
			.get(5, TimeUnit.SECONDS);

		stompSession.subscribe("/topic/greetings", handler);
		stompSession.subscribe("/app/subscription", new EmptyStompHandler());
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

	private static class EmptyStompHandler extends StompSessionHandlerAdapter {

	}

}
