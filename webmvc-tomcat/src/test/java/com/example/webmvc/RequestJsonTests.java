package com.example.webmvc;

import com.example.webmvc.dto.Request;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RequestJsonTests {

	@Autowired
	private JacksonTester<Request> tester;

	@Test
	void parse() throws Exception {
		this.tester.parse("{\"message\":\"Hi\"}").assertThat().extracting(Request::getMessage).isEqualTo("Hi");
	}

	@Test
	void write() throws Exception {
		Request request = new Request();
		request.setMessage("Hello");
		assertThat(this.tester.write(request)).extractingJsonPathValue("message").isEqualTo("Hello");
	}

}
