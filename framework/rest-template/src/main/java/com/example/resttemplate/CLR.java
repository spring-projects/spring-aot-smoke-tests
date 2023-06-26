package com.example.resttemplate;

import java.net.URI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Component
class CLR implements CommandLineRunner {

	private final RestTemplate restTemplate;

	private final String host;

	private final int port;

	private final int tlsPort;

	CLR(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.host = env("HTTPECHO_HOST", "localhost");
		this.port = env("HTTPECHO_PORT_80", 8080);
		this.tlsPort = env("HTTPECHO_PORT_443", 8443);
	}

	@Override
	public void run(String... args) {
		http();
		https();
	}

	private void http() {
		try {
			String response = this.restTemplate
				.getForObject(URI.create("http://%s:%d/".formatted(this.host, this.port)), String.class);
			System.out.printf("http worked: %s%n", response);
		}
		catch (Exception ex) {
			System.out.println("http failed:");
			ex.printStackTrace(System.out);
		}
	}

	private void https() {
		try {
			String response = this.restTemplate
				.getForObject(URI.create("https://%s:%d/".formatted(this.host, this.tlsPort)), String.class);
			System.out.printf("https worked: %s%n", response);
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
