package com.example.json;

import com.example.json.model.Dto;
import com.example.json.model.Dto2;
import com.example.json.model.Dto3;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RegisterReflectionForBinding({Dto.class, Dto2.class, Dto3.class})
public class JsonApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(JsonApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

}
