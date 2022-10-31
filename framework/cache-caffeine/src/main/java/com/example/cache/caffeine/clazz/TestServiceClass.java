package com.example.cache.caffeine.clazz;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestServiceClass {

	private int counter = 1;

	@Cacheable(cacheNames = "class.invoke")
	public void invoke() {
		System.out.printf("class.invoke: %d%n", this.counter);
		this.counter++;
	}

}
