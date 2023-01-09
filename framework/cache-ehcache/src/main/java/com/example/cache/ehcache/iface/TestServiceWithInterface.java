package com.example.cache.ehcache.iface;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
class TestServiceWithInterface implements TestServiceInterface {

	private int counter = 1;

	@Override
	@Cacheable(cacheNames = "interface.invoke")
	public void invoke() {
		System.out.printf("interface.invoke: %d%n", this.counter);
		this.counter++;
	}

}
