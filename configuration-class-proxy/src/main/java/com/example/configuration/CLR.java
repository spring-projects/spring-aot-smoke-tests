package com.example.configuration;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private final BeanFactory beanFactory;

	public CLR(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public void run(String... args) {
		System.out.println("Main: " + this.beanFactory.getBean("text"));
		System.out.println("Nested: " + this.beanFactory.getBean("nestedText"));
	}

}
