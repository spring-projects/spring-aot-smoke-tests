/*
 * Copyright 2026 the original author or authors.
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

package com.example.data.redis;

import org.springframework.data.redis.annotation.RedisListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Messaging {

	public static final String STRING_CHANNEL = "string-channel";

	/**
	 * Declarative channel listener capturing raw {@link String}s received from Redis via
	 * the {@literal string-channel}.
	 * @param data the payload received via the {@literal string-channel}.
	 * @param headers {@link MessageHeaders} when receiving the message.
	 */
	@RedisListener(topic = STRING_CHANNEL)
	public void processStringMessage(@Payload String data, MessageHeaders headers) {

		System.out.printf("message-listener: received [%s] from [%s] at [%s].%n", data, headers.get("channel"),
				headers.getTimestamp());
	}

}
