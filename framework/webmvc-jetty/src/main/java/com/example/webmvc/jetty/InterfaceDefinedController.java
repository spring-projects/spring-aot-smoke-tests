package com.example.webmvc.jetty;

import com.example.webmvc.jetty.dto.Request;
import com.example.webmvc.jetty.dto.Response;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterfaceDefinedController implements ControllerDefinition {

	@Override
	public Response echo(Request request) {
		return new Response(request.getMessage());
	}

}
