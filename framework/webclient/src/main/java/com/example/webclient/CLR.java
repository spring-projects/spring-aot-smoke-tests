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

package com.example.webclient;

import java.time.Duration;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RegisterReflectionForBinding(DataDto.class)
class CLR implements CommandLineRunner {

	private final WebClient httpWebClient;

	private final WebClient httpsWebClient;

	private final DataService dataService;

	CLR(@Qualifier("http") WebClient httpWebClient, @Qualifier("https") WebClient httpsWebClient,
			DataService dataService) {
		this.httpWebClient = httpWebClient;
		this.httpsWebClient = httpsWebClient;
		this.dataService = dataService;
	}

	@Override
	public void run(String... args) {
		http();
		https();
		service();
	}

	private void http() {
		try {
			DataDto dto = this.httpWebClient.get()
				.uri("/anything")
				.retrieve()
				.bodyToMono(DataDto.class)
				.timeout(Duration.ofSeconds(10))
				.block();
			System.out.printf("http: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("http failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void https() {
		try {
			DataDto dto = this.httpsWebClient.get()
				.uri("/anything")
				.retrieve()
				.bodyToMono(DataDto.class)
				.timeout(Duration.ofSeconds(10))
				.block();
			System.out.printf("https: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("https failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void service() {
		try {
			ExchangeDataDto dto = this.dataService.getData();
			System.out.printf("service: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("service failed:");
			ex.printStackTrace(System.out);
		}
	}

}
