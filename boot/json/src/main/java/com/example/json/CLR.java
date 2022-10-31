package com.example.json;

import com.example.json.model.Dto;
import com.example.json.model.Dto2;
import com.example.json.model.Dto3;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private final ObjectMapper objectMapper;

	public CLR(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf("DTO: %s%n", objectMapper.writeValueAsString(new Dto("field-1")));
		System.out.printf("DTO2: %s%n", objectMapper.writeValueAsString(new Dto2("field-2")));
		System.out.printf("DTO3: %s%n", objectMapper.writeValueAsString(new Dto3("field-3")));
	}

}
