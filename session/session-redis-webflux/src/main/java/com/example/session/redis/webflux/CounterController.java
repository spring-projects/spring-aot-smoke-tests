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

package com.example.session.redis.webflux;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

@RestController
@RequestMapping(path = "/counter", produces = MediaType.APPLICATION_JSON_VALUE)
public class CounterController {

	@GetMapping
	public Mono<Map<String, Integer>> index(WebSession session) {
		int counter = session.getAttributeOrDefault("counter", 1);
		session.getAttributes().put("counter", counter + 1);
		return Mono.just(Map.of("counter", counter));
	}

}
