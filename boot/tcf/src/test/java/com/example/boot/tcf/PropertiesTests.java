package com.example.boot.tcf;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "test=1")
class PropertiesTests {

	@Value("${test}")
	private String test;

	@Test
	void shouldInjectProperty() {
		assertThat(this.test).isEqualTo("1");
	}

	@Test
	void shouldInjectEnvironment(@Autowired Environment env) {
		assertThat(env.getProperty("test")).isEqualTo("1");
	}

}
