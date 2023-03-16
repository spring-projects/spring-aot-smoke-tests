package com.example.rsocket;

import com.example.rsocket.dto.Message;
import com.example.rsocket.dto.MessageRecord;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.rsocket.context.RSocketServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketRequester.Builder;
import org.springframework.stereotype.Component;

@Component
class RSocketClient implements CommandLineRunner {

	private int port;

	private final RSocketRequester.Builder builder;

	RSocketClient(Builder builder) {
		this.builder = builder;
	}

	@Override
	public void run(String... args) {
		RSocketRequester requester = this.builder.tcp("localhost", this.port);

		message(requester);
		reactiveMessage(requester);
		messageRecord(requester);
		messageExceptionHandler(requester);
	}

	@EventListener
	public void onRSocketServerStart(RSocketServerInitializedEvent event) {
		this.port = event.getServer().address().getPort();
	}

	private void message(RSocketRequester requester) {
		Message message = requester.route("message")
			.data(new Message("client", "Hello!"))
			.retrieveMono(Message.class)
			.block();
		System.out.printf("Client: message(): %s%n", message);
	}

	private void reactiveMessage(RSocketRequester requester) {
		Message message = requester.route("reactive-message")
			.data(new Message("client", "Hello!"))
			.retrieveMono(Message.class)
			.block();
		System.out.printf("Client: reactiveMessage(): %s%n", message);
	}

	private void messageRecord(RSocketRequester requester) {
		MessageRecord messageRecord = requester.route("message-record")
			.data(new MessageRecord("client", "Hello!"))
			.retrieveMono(MessageRecord.class)
			.block();
		System.out.printf("Client: messageRecord(): %s%n", messageRecord);
	}

	private void messageExceptionHandler(RSocketRequester requester) {
		requester.route("illegal-state-exception").send().block();
		System.out.printf("Client: messageExceptionHandler()");
	}

}
