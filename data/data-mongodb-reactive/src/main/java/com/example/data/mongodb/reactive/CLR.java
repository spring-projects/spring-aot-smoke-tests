package com.example.data.mongodb.reactive;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ReactiveMongoTemplate template;

	@Autowired
	private OrderRepository repository;

	@Override
	public void run(String... args) {

		LineItem product1 = new LineItem("p1", 1.23);
		LineItem product2 = new LineItem("p2", 0.87, 2);
		LineItem product3 = new LineItem("p3", 5.33);

		// Basic save find via repository
		{
			System.out.println("---- FIND ALL ----");

			repository.deleteAll().block();

			Order order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
			repository.save(order).block();

			Iterable<Order> all = repository.findAll().toIterable();
			all.forEach(System.out::println);

			System.out.println("-----------------\n\n\n");
		}

		{
			System.out.println("---- Paging / Sorting ----");
			repository.deleteAll().block();

			repository.save(new Order("c42", new Date()).addItem(product1)).block();
			repository.save(new Order("c42", new Date()).addItem(product2)).block();
			repository.save(new Order("c42", new Date()).addItem(product3)).block();

			repository.save(new Order("b12", new Date()).addItem(product1)).block();
			repository.save(new Order("b12", new Date()).addItem(product1)).block();

			// sort
			List<Order> sortedByCustomer = repository.findBy(Sort.by("customerId")).collectList().block();
			System.out.println("sortedByCustomer: " + sortedByCustomer);

			// page
			List<Order> c42_page0 = repository.findByCustomerId("c42", PageRequest.of(0, 2)).collectList().block();
			System.out.println("c42_page0: " + c42_page0);
			List<Order> c42_page1 = repository.findByCustomerId("c42", PageRequest.of(1, 2)).collectList().block();
			System.out.println("c42_page1: " + c42_page1);

			// slice
			List<Order> c42_slice0 = repository.findSliceByCustomerId("c42", PageRequest.of(0, 2))
				.collectList()
				.block();
			System.out.println("c42_slice0: " + c42_slice0);

			System.out.println("-----------------\n\n\n");
		}

		// Part Tree Query
		{
			System.out.println("---- PART TREE QUERY ----");
			repository.deleteAll().block();

			Order order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
			repository.save(order).block();

			List<Order> byCustomerId = repository.findByCustomerId(order.getCustomerId()).collectList().block();
			System.out.print("derivedQuery: ");
			byCustomerId.forEach(System.out::println);

			System.out.println("-----------------\n\n\n");
		}

		// Annotated Query
		{
			System.out.println("---- ANNOTATED QUERY ----");
			repository.deleteAll().block();

			Order order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
			repository.save(order).block();

			List<Order> byCustomerId = repository.findByCustomerViaAnnotation(order.getCustomerId())
				.collectList()
				.block();
			System.out.print("annotatedQuery: ");
			byCustomerId.forEach(System.out::println);

			System.out.println("-----------------\n\n\n");
		}

		// Annotated Aggregations
		{
			System.out.println("---- ANNOTATED AGGREGATIONS ----");
			repository.deleteAll().block();

			repository.save(new Order("c42", new Date()).addItem(product1)).block();
			repository.save(new Order("c42", new Date()).addItem(product2)).block();
			repository.save(new Order("c42", new Date()).addItem(product3)).block();

			repository.save(new Order("b12", new Date()).addItem(product1)).block();
			repository.save(new Order("b12", new Date()).addItem(product1)).block();

			List<OrdersPerCustomer> result = repository.totalOrdersPerCustomer(Sort.by(Sort.Order.desc("total")))
				.collectList()
				.block();
			System.out.println("aggregation: " + result);

			System.out.println("-----------------\n\n\n");
		}

		// Custom Implementation
		{
			System.out.println("---- CUSTOM IMPLEMENTATION ----");
			repository.deleteAll().block();

			Order order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
			order = repository.save(order).block();

			Invoice invoice = repository.getInvoiceFor(order).block();
			System.out.println("invoice: " + invoice);

			System.out.println("-----------------\n\n\n");
		}

		// Result Projection
		{
			System.out.println("---- RESULT PROJECTION ----");
			repository.deleteAll().block();

			Order order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
			repository.save(order).block();

			List<OrderProjection> result = repository.findOrderProjectionByCustomerId(order.getCustomerId())
				.collectList()
				.block();

			System.out.print("projection: ");
			result.forEach(it -> System.out.println(String.format("OrderProjection(%s){id=%s, customerId=%s}",
					it.getClass().getSimpleName(), it.getId(), it.getCustomerId())));
			System.out.println("-----------------\n\n\n");
		}

		{

			System.out.println("---- QUERY BY EXAMPLE ----");
			repository.deleteAll().block();

			Order order1 = repository.save(new Order("c42", new Date()).addItem(product1)).block();
			Order order2 = repository.save(new Order("b12", new Date()).addItem(product1)).block();

			Example<Order> example = Example.of(new Order(order1.getCustomerId()),
					ExampleMatcher.matching().withIgnorePaths("items"));
			Iterable<Order> result = repository.findAll(example).toIterable();

			System.out.print("qbe: ");
			result.forEach(System.out::print);
			System.out.println();

			System.out.println("-----------------\n\n\n");
		}

		this.personRepository.save(new Person("first-1", "last-1")).block();
		this.personRepository.save(new Person("first-2", "last-2")).block();
		this.personRepository.save(new Person("first-3", "last-3")).block();

		this.personRepository.findAll().subscribe((person) -> {
			System.out.printf("findAll(): %s%n", person);
		}, ex -> ex.printStackTrace(System.out));

		this.personRepository.findByLastname("last-3").subscribe((person) -> {
			System.out.printf("findByLastname(): %s%n", person);
		}, ex -> ex.printStackTrace(System.out));
	}

}
