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

import com.example.webflux.dto.Request;
import com.example.webflux.dto.Response;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
public class XmlController {

	@GetMapping
	public Mono<Response> response() {
		return Mono.just(new Response("Hello XML"));
	}

	@PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
	public Mono<Response> echo(@RequestBody Mono<Request> request) {
		return request.map((r) -> new Response("Server: " + r.getMessage()));
	}

}
