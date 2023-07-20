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
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
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
