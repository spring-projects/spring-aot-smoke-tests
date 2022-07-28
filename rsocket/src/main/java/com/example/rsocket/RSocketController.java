package com.example.rsocket;

import com.example.rsocket.dto.Message;
import com.example.rsocket.dto.MessageRecord;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RSocketController {

	@MessageMapping("message")
	public Message message(Message request) {
		System.out.printf("Server: message(): %s%n", request);
		return new Message("server", request.getMessage());
	}

	@MessageMapping("reactive-message")
	public Mono<Message> reactiveMessage(Message request) {
		System.out.printf("Server: reactiveMessage: %s%n", request);
		return Mono.just(new Message("server", request.getMessage()));
	}

	@MessageMapping("message-record")
	public MessageRecord messageRecord(MessageRecord request) {
		System.out.printf("Server: messageRecord(): %s%n", request);
		return new MessageRecord("server", request.message());
	}

}
