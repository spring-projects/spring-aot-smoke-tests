package com.example.mustache.webflux;

import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(NettyRuntimeHints.class)
public class MustacheWebFluxApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MustacheWebFluxApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
