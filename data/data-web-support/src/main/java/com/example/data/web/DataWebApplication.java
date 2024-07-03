/*
 * Copyright 2024 the original author or authors.
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
package com.example.data.web;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class DataWebApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataWebApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@RestController
	static class DefaultController {

		@GetMapping("/paged")
		public Page paged() {
			return new PageImpl(List.of("spring", "data", "web", "support", "paged"), PageRequest.of(2, 5), 100);
		}

		@GetMapping("/unpaged")
		public Page unpaged() {
			return new PageImpl(List.of("spring", "data", "web", "support", "unpaged"), Pageable.unpaged(), 5);
		}

	}

}
