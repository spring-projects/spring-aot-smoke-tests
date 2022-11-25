/*
 * Copyright 2022 the original author or authors.
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

package com.example.webmvc.undertow.tls;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.aot.smoketest.support.junit.ApplicationUrl;
import org.springframework.aot.smoketest.support.junit.ApplicationUrl.Scheme;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class WebMvcUndertowTlsApplicationAotTests {

	@Test
	void stringResponseBody(@ApplicationUrl(scheme = Scheme.HTTPS) URI applicationUrl) throws Exception {
		WebTestClient client = buildWebClient(applicationUrl);

		client.get().exchange().expectStatus().isOk().expectBody().consumeWith(
				(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Hello World TLS"));
	}

	/**
	 * Builds a web client for the running application. This web client is configured to
	 * trust the TLS certificate of the server.
	 * @param applicationUrl the URL to the application
	 * @return web client
	 * @throws Exception if something went wrong
	 */
	private static WebTestClient buildWebClient(URI applicationUrl) throws Exception {
		KeyStore trustStore = KeyStore.getInstance("jks");
		try (InputStream stream = WebMvcUndertowTlsApplicationAotTests.class.getResourceAsStream("/truststore.jks")) {
			assertThat(stream).as("Trust store on test classpath").isNotNull();
			trustStore.load(stream, null);
		}

		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(trustStore);

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

		HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
		ClientHttpConnector connector = new JdkClientHttpConnector(httpClient);
		return WebTestClient.bindToServer(connector).baseUrl(applicationUrl.toString()).build();
	}

}
