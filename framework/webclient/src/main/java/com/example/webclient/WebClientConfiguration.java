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
	HttpEchoConfig httpEchoConfig() {
		return HttpEchoConfig.loadFromEnv();
	}

	@Bean
	@Qualifier("https")
	WebClient webClientHttps(HttpEchoConfig httpEchoConfig, WebClient.Builder builder) throws SSLException {
		SslContext sslContext = SslContextBuilder.forClient()
			.trustManager(InsecureTrustManagerFactory.INSTANCE)
			.build();
		HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
		return builder.baseUrl(httpEchoConfig.httpsBaseUrl())
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();
	}

	@Bean
	@Qualifier("http")
	WebClient webClientHttp(HttpEchoConfig httpEchoConfig, WebClient.Builder builder) {
		return builder.baseUrl(httpEchoConfig.httpBaseUrl()).build();
	}

	@Bean
	DataService dataService(@Qualifier("http") WebClient webClient) {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
			.build();
		return factory.createClient(DataService.class);
	}

	record HttpEchoConfig(String host, int port, int tlsPort) {
		public static HttpEchoConfig loadFromEnv() {
			String host = env("HTTPECHO_HOST", "localhost");
			int port = env("HTTPECHO_PORT_80", 8080);
			int tlsPort = env("HTTPECHO_PORT_443", 8443);
			return new HttpEchoConfig(host, port, tlsPort);
		}

		String httpsBaseUrl() {
			return "https://%s:%d/".formatted(this.host, this.tlsPort);
		}

		String httpBaseUrl() {
			return "http://%s:%d/".formatted(this.host, this.port);
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

}
