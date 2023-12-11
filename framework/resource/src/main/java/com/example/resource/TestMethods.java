package com.example.resource;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
class TestMethods {

	private String hello;

	private String test;

	private Integer number;

	@Resource
	public void setHello(String hello) {
		this.hello = hello;
	}

	@Resource
	public void setTest(String test) {
		this.test = test;
	}

	@Resource
	public void setNumber(Integer number) {
		this.number = number;
	}

	public String describe() {
		return "hello='%s',test='%s',number=%s".formatted(this.hello, this.test, this.number);
	}

}
