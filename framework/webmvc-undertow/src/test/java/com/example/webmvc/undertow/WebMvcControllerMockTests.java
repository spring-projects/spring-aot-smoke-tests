package com.example.webmvc.undertow;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WebMvcControllerMockTests {

	@Test
	void check() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebMvcController()).build();
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(content().string("Hello from Spring MVC and Undertow"));
	}

}
