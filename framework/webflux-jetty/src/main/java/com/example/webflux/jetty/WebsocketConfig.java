package com.example.webflux.jetty;

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
