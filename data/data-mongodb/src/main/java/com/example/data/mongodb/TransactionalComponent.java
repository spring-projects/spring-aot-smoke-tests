/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
