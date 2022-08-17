package com.example.cache.simple;

import org.springframework.stereotype.Service;

@Service
class TestServiceImpl implements TestService {

	private int counter = 1;

	@Override
	public void invoke() {
		System.out.printf("invoke: %d%n", this.counter);
		this.counter++;
	}

}
