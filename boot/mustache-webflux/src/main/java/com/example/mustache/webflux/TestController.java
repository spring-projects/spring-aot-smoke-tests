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

package com.example.mustache.webflux;

import java.util.List;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class TestController {

	@GetMapping("/greeting")
    @RegisterReflectionForBinding(IndexModel.class)
	public String index(@RequestParam(defaultValue = "world") String name, Model model) {
		model.addAttribute("model", new IndexModel("Hello", name));
		return "index";
	}

	@GetMapping("/authors")
    @RegisterReflectionForBinding(Author.class)
	public String authors(Model model) {
		model.addAttribute("authors", List.of(new Author("Brian Goetz"), new Author("Joshua Bloch")));
		return "authors";
	}

	public record IndexModel(String greeting, String name) {
	}

	public record Author(String name) {
	}

}
