package com.example.cache.ehcache;

import com.example.cache.ehcache.clazz.TestServiceClass;
import com.example.cache.ehcache.iface.TestServiceInterface;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class CLR implements CommandLineRunner {

	private final TestServiceClass testServiceClass;

	private final TestServiceInterface testServiceInterface;

	public CLR(TestServiceClass testServiceClass, TestServiceInterface testServiceInterface) {
		this.testServiceClass = testServiceClass;
		this.testServiceInterface = testServiceInterface;
	}

	@Override
	public void run(String... args) {
		this.testServiceClass.invoke();
		this.testServiceClass.invoke();

		this.testServiceInterface.invoke();
		this.testServiceInterface.invoke();
	}

}
