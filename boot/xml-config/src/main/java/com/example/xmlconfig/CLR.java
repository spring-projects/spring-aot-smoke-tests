package com.example.xmlconfig;

import com.example.xmlconfig.service.MyService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private final MyService myService;

	public CLR(MyService myService) {
		this.myService = myService;
	}

	@Override
	public void run(String... args) {
		this.myService.sayHello();
	}

}
