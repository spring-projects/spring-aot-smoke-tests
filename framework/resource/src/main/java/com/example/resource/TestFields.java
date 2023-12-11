package com.example.resource;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
class TestFields {

	@Resource
	private String hello;

	@Resource
	private String test;

	@Resource
	private Integer number;

	public String describe() {
		return "hello='%s',test='%s',number=%s".formatted(this.hello, this.test, this.number);
	}

}
