package com.example.webmvc.jetty;

import com.example.webmvc.jetty.dto.Request;
import com.example.webmvc.jetty.dto.Response;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ControllerDefinition {

	@PostMapping(value = "/echo", produces = { "application/json" }, consumes = { "application/json" })
	Response echo(@RequestBody Request request);

}
