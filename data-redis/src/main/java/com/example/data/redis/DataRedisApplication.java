package com.example.data.redis;

import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(NettyRuntimeHints.class)
public class DataRedisApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataRedisApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
