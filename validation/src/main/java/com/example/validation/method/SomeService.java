package com.example.validation.method;

import jakarta.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
class SomeService {

	public String hello(@NotBlank String name) {
		return "hello " + name;
	}

}
