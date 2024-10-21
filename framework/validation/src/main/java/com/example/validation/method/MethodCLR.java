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

package com.example.validation.method;

import jakarta.validation.ConstraintViolationException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class MethodCLR implements CommandLineRunner {

	private final SomeService someService;

	MethodCLR(SomeService someService) {
		this.someService = someService;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf("this.someService.hello('world'): %s%n", this.someService.hello("world"));
		try {
			this.someService.hello("");
			System.out.println("this.someService.hello(''): Invocation worked, this should not have happened!");
		}
		catch (ConstraintViolationException e) {
			System.out.println("this.someService.hello(''): Got expected exception");
		}
	}

}
