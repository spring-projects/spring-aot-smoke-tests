package com.example.webmvc.jetty;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled("https://github.com/spring-projects/spring-boot/issues/33044")
class AutoConfigureMockMvcTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldGetResponse() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string("Hello from Spring MVC and Jetty"));
	}

}
