/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.json;

import com.example.json.model.Dto;
import com.example.json.model.Dto2;
import com.example.json.model.Dto3;
import tools.jackson.databind.ObjectMapper;

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
