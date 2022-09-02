package com.example.data.redis.reactive;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(NettyRuntimeHints.class)
@RegisterReflectionForBinding(Person.class)
public class DataRedisReactiveApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataRedisReactiveApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
