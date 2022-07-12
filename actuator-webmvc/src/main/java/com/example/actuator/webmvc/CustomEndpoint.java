package com.example.actuator.webmvc;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom")
class CustomEndpoint {

	@ReadOperation
	String read() {
		return "custom-read";
	}

}
