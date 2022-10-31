package com.example.commandlinerunner.service;

import org.springframework.stereotype.Service;

@Service
class MyServiceImpl implements MyService {

	@Override
	public void sayHello() {
		System.out.println("Hello from MyServiceImpl");
	}

}
