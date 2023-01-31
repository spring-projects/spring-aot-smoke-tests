package com.example.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(BatchApplication.BatchApplicationRuntimeHints.class)
public class BatchApplication {

	public static void main(String[] args) throws InterruptedException {
		List<String> newArgs = new ArrayList<>(Arrays.asList(args));
		newArgs.add("fileName=persons.csv");
		SpringApplication.run(BatchApplication.class, newArgs.toArray(String[]::new));
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class BatchApplicationRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerPattern("persons.csv");

			// TODO remove the follwoing hint once
			// https://github.com/spring-projects/spring-batch/issues/4248 is resolved
			hints.proxies().registerJdkProxy(AopProxyUtils.completeJdkProxyInterfaces(JobOperator.class));
		}

	}

}
