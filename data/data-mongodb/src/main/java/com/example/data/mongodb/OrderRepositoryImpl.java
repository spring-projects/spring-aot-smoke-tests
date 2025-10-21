/*
 * Copyright 2022 the original author or authors.
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

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import com.example.data.mongodb.OrderRepositoryImpl.Hints;
import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

@Component
@ImportRuntimeHints(Hints.class)
class OrderRepositoryImpl implements OrderRepositoryCustom {

	private final MongoOperations operations;

	private double taxRate = 0.19;

	public OrderRepositoryImpl(MongoOperations operations) {
		this.operations = operations;
	}

	@Override
	public Invoice getInvoiceFor(Order order) {

		AggregationResults<Invoice> results = operations.aggregate(newAggregation(Order.class, //
				match(where("id").is(order.getId())), //
				unwind("items"), //
				project("id", "customerId", "items") //
					.andExpression("'$items.price' * '$items.quantity'")
					.as("lineTotal"), //
				group("id") //
					.sum("lineTotal")
					.as("netAmount") //
					.addToSet("items")
					.as("items"), //
				project("id", "items", "netAmount") //
					.and("orderId")
					.previousOperation() //
					.andExpression("netAmount * [0]", taxRate)
					.as("taxAmount") //
					.andExpression("netAmount * (1 + [0])", taxRate)
					.as("totalAmount") //
		), Invoice.class);

		return results.getUniqueMappedResult();
	}

	static class Hints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
			hints.reflection()
				.registerType(Invoice.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
						MemberCategory.INVOKE_PUBLIC_METHODS);
		}

	}

}
