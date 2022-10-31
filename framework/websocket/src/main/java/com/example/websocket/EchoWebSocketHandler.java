package com.example.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EchoWebSocketHandler extends TextWebSocketHandler {

	public EchoWebSocketHandler() {
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.printf("Opened new session in instance %s%n", this);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.printf("Server: Received '%s' in %s%n", message.getPayload(), this);
		session.sendMessage(message);
		System.out.printf("Server: Sent '%s' in %s%n", message.getPayload(), this);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.printf("Closing connection in %s%n", this, exception);
		session.close(CloseStatus.SERVER_ERROR);
	}

}
