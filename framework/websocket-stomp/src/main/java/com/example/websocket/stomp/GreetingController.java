package com.example.websocket.stomp;

import com.example.websocket.stomp.dto.GreetingMessage;
import com.example.websocket.stomp.dto.HelloMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	@SubscribeMapping("/subscription")
	public void subscribe() {
		System.out.println("Server: subscription");
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public GreetingMessage greeting(HelloMessage message) throws Exception {
		System.out.printf("Server: Received '%s'%n", message);
		Thread.sleep(1000); // simulated delay
		return new GreetingMessage("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

}
