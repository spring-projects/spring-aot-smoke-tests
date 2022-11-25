package com.example.webmvc.jetty.tls;

import com.example.webmvc.jetty.tls.WebMvcJettyTlsApplication.KeyStoreRuntimeHints;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(KeyStoreRuntimeHints.class)
public class WebMvcJettyTlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebMvcJettyTlsApplication.class, args);
	}

	static class KeyStoreRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerPattern("keystore.jks");
		}

	}

}
