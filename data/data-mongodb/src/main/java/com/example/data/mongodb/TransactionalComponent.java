package com.example.data.mongodb;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class TransactionalComponent {

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public void runTransactional(LineItem product) {

		Order savedOrder = orderRepository.save(newOrder("within-tx", product));
		System.out.printf("in-transaction: %s%n", savedOrder);
		throw new RuntimeException("rollback");
	}

	private Order newOrder(String customerId, LineItem... items) {
		return newOrder(customerId, new Date(), items);
	}

	private Order newOrder(String customerId, Date date, LineItem... items) {

		Order order = new Order(customerId, date);

		Arrays.stream(items).forEach(order::addItem);

		return order;
	}

}
