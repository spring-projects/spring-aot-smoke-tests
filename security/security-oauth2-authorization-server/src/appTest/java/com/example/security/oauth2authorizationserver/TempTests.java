/*
 * Copyright 2025 the original author or authors.
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

package com.example.security.oauth2authorizationserver;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author pwebb
 */
public class TempTests {

	@Test
	void performTokenRequestWhenInvalidClientCredentialsThenUnauthorized() {
		WebClient client = WebClient.create("http://localhost:8080");
		// @formatter:off
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "client_credentials");
		formData.add("scope", "message:read");
		System.out.println(
		client.post().uri("/oauth2/token").headers(badCredentials())
				.body(BodyInserters.fromFormData(formData))
				.exchange().block().statusCode());


//		.expectStatus().isUnauthorized()
//				.expectBody().jsonPath("$.error").isEqualTo("invalid_client");
		// @formatter:on
	}

	private static Consumer<HttpHeaders> badCredentials() {
		return (headers) -> headers.setBasicAuth("bad", "password");
	}

}
