package com.example.cache.hazelcast;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
class TestService {

	private int counter = 1;

	@Cacheable(cacheNames = "invoke")
	public void invoke() {
		System.out.printf("invoke: %d%n", this.counter);
		this.counter++;
	}

}
