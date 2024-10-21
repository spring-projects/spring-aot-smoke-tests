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

package com.example.session.jdbc;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/counter", produces = MediaType.APPLICATION_JSON_VALUE)
public class CounterController {

	@GetMapping
	public Map<String, Integer> counter(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("counter") == null) {
			session.setAttribute("counter", 1);
		}
		int counter = (int) session.getAttribute("counter");
		session.setAttribute("counter", counter + 1);
		return Map.of("counter", counter);
	}

}
