package com.example.order;

import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApplication {

	private static final Log logger = LogFactory.getLog(OrderApplication.class);

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(OrderApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	public ApplicationRunner run(ObjectProvider<Item> items) {
		return (args) -> {
			String itemsDescription = items.orderedStream().map(Item::describe).collect(Collectors.joining(", "));
			logger.info("Items: " + itemsDescription);
		};
	}

}
