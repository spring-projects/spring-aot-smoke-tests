package com.example.webmvc.undertow.tls;

import com.example.webmvc.undertow.tls.WebMvcUndertowTlsApplication.KeyStoreRuntimeHints;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(KeyStoreRuntimeHints.class)
public class WebMvcUndertowTlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebMvcUndertowTlsApplication.class, args);
	}

	static class KeyStoreRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerPattern("keystore.jks");
		}

	}

}
