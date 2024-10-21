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
