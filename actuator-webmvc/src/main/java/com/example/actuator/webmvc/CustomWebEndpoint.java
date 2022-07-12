package com.example.actuator.webmvc;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

@Component
@WebEndpoint(id = "customWeb")
class CustomWebEndpoint {

	@ReadOperation
	WebEndpointResponse<String> read() {
		return new WebEndpointResponse<>("customWeb-read", 299);
	}

}
