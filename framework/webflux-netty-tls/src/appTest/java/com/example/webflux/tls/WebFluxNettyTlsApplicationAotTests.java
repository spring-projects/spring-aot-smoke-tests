package com.example.webflux.tls;

import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.TrustManagerFactory;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.aot.smoketest.support.junit.ApplicationUrl;
import org.springframework.aot.smoketest.support.junit.ApplicationUrl.Scheme;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class WebFluxNettyTlsApplicationAotTests {

	@Test
	void stringResponseBody(@ApplicationUrl(scheme = Scheme.HTTPS) URI applicationUrl) throws Exception {
		WebTestClient client = buildWebClient(applicationUrl);

		client.get()
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith(
					(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Hello World TLS"));
	}

	@Test
	void websocket(@ApplicationUrl(scheme = Scheme.SECURE_WEBSOCKET) URI applicationUrl) throws Exception {
		WebSocketClient client = new ReactorNettyWebSocketClient(buildHttpClient());

		// We can't use StepVerifier here, as it isn't designed to be used in a reactive
		// pipeline
		AtomicReference<List<String>> messages = new AtomicReference<>();
		client
			.execute(URI.create(applicationUrl.resolve("/ws/count").toString()),
					session -> session.receive()
						.map(WebSocketMessage::getPayloadAsText)
						.collectList()
						.doOnNext(messages::set)
						.then())
			.block(Duration.ofSeconds(10));
		assertThat(messages.get()).isNotNull().containsExactly("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
	}

	/**
	 * Builds a web client for the running application. This web client is configured to
	 * trust the TLS certificate of the server.
	 * @param applicationUrl the URL to the application
	 * @return web client
	 * @throws Exception if something went wrong
	 */
	private static WebTestClient buildWebClient(URI applicationUrl) throws Exception {
		ClientHttpConnector connector = new ReactorClientHttpConnector(buildHttpClient());
		return WebTestClient.bindToServer(connector).baseUrl(applicationUrl.toString()).build();
	}

	/**
	 * Builds a http client for the running application. This http client is configured to
	 * trust the TLS certificate of the server.
	 * @return http client
	 * @throws Exception if something went wrong
	 */
	private static HttpClient buildHttpClient() throws Exception {
		KeyStore trustStore = KeyStore.getInstance("jks");
		try (InputStream stream = WebFluxNettyTlsApplicationAotTests.class.getResourceAsStream("/truststore.jks")) {
			assertThat(stream).as("Trust store on test classpath").isNotNull();
			trustStore.load(stream, null);
		}

		TrustManagerFactory trustManagerFactory = TrustManagerFactory
			.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(trustStore);

		SslContext sslContext = SslContextBuilder.forClient().trustManager(trustManagerFactory).build();
		return HttpClient.create().secure((s) -> s.sslContext(sslContext));
	}

}
