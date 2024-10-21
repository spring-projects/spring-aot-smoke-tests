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

import javax.net.ssl.SSLException;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
class WebClientConfiguration {

	@Bean
	@Qualifier("https")
	WebClient webClientHttps(WebClient.Builder builder) throws SSLException {
		SslContext sslContext = SslContextBuilder.forClient()
			.trustManager(InsecureTrustManagerFactory.INSTANCE)
			.build();
		HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
		String host = env("HTTPBIN_TLS_HOST", "localhost");
		int port = env("HTTPBIN_TLS_PORT_8443", 8443);
		return builder.baseUrl("https://%s:%d/".formatted(host, port))
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();
	}

	@Bean
	@Qualifier("http")
	WebClient webClientHttp(WebClient.Builder builder) {
		String host = env("HTTPBIN_HOST", "localhost");
		int port = env("HTTPBIN_PORT_8080", 8080);
		return builder.baseUrl("http://%s:%d/".formatted(host, port)).build();
	}

	@Bean
	DataService dataService(@Qualifier("http") WebClient webClient) {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
			.build();
		return factory.createClient(DataService.class);
	}

	private static String env(String name, String def) {
		String value = System.getenv(name);
		return StringUtils.hasLength(value) ? value : def;
	}

	private static int env(String name, int def) {
		String value = System.getenv(name);
		return StringUtils.hasLength(value) ? Integer.parseInt(value) : def;
	}

}
