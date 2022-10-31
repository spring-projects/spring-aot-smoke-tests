package com.example.webmvc;

import com.example.webmvc.dto.SampleRecord;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebMvcController {

	@GetMapping("/")
	public String textPlainStringResponseBody() {
		return "Hello from Spring MVC and Tomcat";
	}

	@GetMapping("/record")
	public SampleRecord recordSerializedToJson() {
		return new SampleRecord("Hello from Spring MVC and Tomcat");
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@GetMapping("/status")
	public String customResponseStatus() {
		return "status";
	}

}
