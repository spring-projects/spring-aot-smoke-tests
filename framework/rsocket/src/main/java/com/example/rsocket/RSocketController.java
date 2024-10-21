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

package com.example.rsocket;

import com.example.rsocket.dto.Message;
import com.example.rsocket.dto.MessageRecord;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
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

	@MessageMapping("illegal-state-exception")
	public void throwIllegalStateException() {
		throw new IllegalStateException();
	}

	@MessageExceptionHandler({ IllegalStateException.class })
	public void handleIllegalStateException() {
		System.out.printf("Server: handleIllegalStateException()");
	}

}
