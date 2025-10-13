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

package com.example.websocket;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
class CLR implements CommandLineRunner {

	private final WebSocketClient webSocketClient;

	private final ServletWebServerApplicationContext servletWebServerApplicationContext;

	CLR(WebSocketClient webSocketClient, ServletWebServerApplicationContext servletWebServerApplicationContext) {
		this.webSocketClient = webSocketClient;
		this.servletWebServerApplicationContext = servletWebServerApplicationContext;
	}

	@Override
	public void run(String... args) throws Exception {
		WebSocketSession session = this.webSocketClient
			.execute(new MyWebsocketHandler(), null, URI.create("ws://localhost:%d/echo".formatted(getServerPort())))
			.get(5, TimeUnit.SECONDS);
		TextMessage message = new TextMessage("Hello Websocket");
		session.sendMessage(message);
		System.out.printf("Client: Sent '%s'%n", message.getPayload());
	}

	private int getServerPort() {
		return this.servletWebServerApplicationContext.getWebServer().getPort();
	}

	private static class MyWebsocketHandler extends TextWebSocketHandler {

		@Override
		protected void handleTextMessage(WebSocketSession session, TextMessage message) {
			System.out.printf("Client: Received '%s'%n", message.getPayload());
		}

	}

}
