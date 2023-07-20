package com.example.resttemplate;

import java.net.URI;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Component
@RegisterReflectionForBinding(DataDto.class)
class CLR implements CommandLineRunner {

	private final RestTemplate restTemplate;

	private final String httpHost;

	private final int httpPort;

	private final String httpsHost;

	private final int httpsPort;

	CLR(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.httpHost = env("HTTPBIN_HOST", "localhost");
		this.httpsHost = env("HTTPBIN_TLS_HOST", "localhost");
		this.httpPort = env("HTTPBIN_PORT_8080", 8080);
		this.httpsPort = env("HTTPBIN_TLS_PORT_8443", 8443);
	}

	@Override
	public void run(String... args) {
		http();
		https();
	}

	private void http() {
		try {
			DataDto dto = restTemplate.getForObject(
					URI.create("http://%s:%d/anything".formatted(this.httpHost, this.httpPort)), DataDto.class);
			System.out.printf("http: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("http failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void https() {
		try {
			DataDto dto = restTemplate.getForObject(
					URI.create("https://%s:%d/anything".formatted(this.httpsHost, this.httpsPort)), DataDto.class);
			System.out.printf("https: %s%n", dto);
		}
		catch (Exception ex) {
			System.out.println("https failed:");
			ex.printStackTrace(System.out);
		}
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
