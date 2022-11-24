package com.example.webmvc.jetty;

import com.example.webmvc.jetty.dto.Request;
import com.example.webmvc.jetty.dto.Response;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
public class XmlController {

	@PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
	public Response echo(@RequestBody Request request) {
		return new Response("Server: " + request.getMessage());
	}

}
