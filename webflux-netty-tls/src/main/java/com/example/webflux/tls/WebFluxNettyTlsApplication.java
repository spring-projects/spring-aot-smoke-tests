package com.example.webflux.tls;

import com.example.webflux.tls.WebFluxNettyTlsApplication.KeyStoreRuntimeHints;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints({ NettyRuntimeHints.class, KeyStoreRuntimeHints.class })
public class WebFluxNettyTlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxNettyTlsApplication.class, args);
	}

	static class KeyStoreRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerPattern("keystore.jks");
		}

	}

}
