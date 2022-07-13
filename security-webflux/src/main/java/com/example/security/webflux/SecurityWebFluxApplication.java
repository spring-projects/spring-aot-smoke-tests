package com.example.security.webflux;

import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(NettyRuntimeHints.class)
public class SecurityWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityWebFluxApplication.class, args);
	}

}
