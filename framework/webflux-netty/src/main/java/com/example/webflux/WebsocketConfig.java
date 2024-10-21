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

package com.example.webflux;

import java.time.Duration;
import java.util.Map;

import reactor.core.publisher.Flux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;

@Configuration(proxyBeanMethods = false)
class WebsocketConfig {

	@Bean
	WebSocketHandler webSocketHandler() {
		return webSocketSession -> {
			Flux<WebSocketMessage> stream = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.map(element -> webSocketSession.textMessage(Integer.toString(element)))
				.delayElements(Duration.ofMillis(100));
			return webSocketSession.send(stream);
		};
	}

	@Bean
	public SimpleUrlHandlerMapping body(WebSocketHandler webSocketHandler) {
		Map<String, WebSocketHandler> handlers = Map.of("/ws/count", webSocketHandler);
		return new SimpleUrlHandlerMapping(handlers, 10);
	}

}
