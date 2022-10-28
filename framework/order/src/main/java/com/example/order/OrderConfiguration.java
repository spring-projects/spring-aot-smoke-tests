package com.example.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration(proxyBeanMethods = false)
class OrderConfiguration {

	@Bean
	Item unorderedItem() {
		return () -> "none";
	}

	@Bean
	PriorityOrderedItem priorityItem50() {
		return new PriorityOrderedItem(50);
	}

	@Bean
	@Order(-10)
	Item itemMinus10() {
		return () -> "-10";
	}

}
