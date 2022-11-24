package com.example.boot.tcf;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, useMainMethod = UseMainMethod.ALWAYS)
class MainMethodTests {

	@Test
	void mainShouldRun() {
		String property = System.getProperty("main.ran", "false");
		assertThat(property).isEqualTo("true");
	}

}
