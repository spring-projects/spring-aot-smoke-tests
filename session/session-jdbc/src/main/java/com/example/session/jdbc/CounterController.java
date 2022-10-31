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
