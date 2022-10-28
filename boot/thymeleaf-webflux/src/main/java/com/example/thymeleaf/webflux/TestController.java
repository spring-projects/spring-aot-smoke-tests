package com.example.thymeleaf.webflux;

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
