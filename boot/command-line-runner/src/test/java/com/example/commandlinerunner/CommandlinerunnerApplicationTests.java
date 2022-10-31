package com.example.commandlinerunner;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, useMainMethod = UseMainMethod.ALWAYS, args = "--test.args=1")
class CommandlinerunnerApplicationTests {

	@Test
	void mainShouldRun() {
		String property = System.getProperty("main.ran", "false");
		assertThat(property).isEqualTo("true");
	}

	@Test
	void shouldPassArgs(@Autowired ApplicationArguments applicationArguments) {
		List<String> values = applicationArguments.getOptionValues("test.args");
		assertThat(values).containsExactly("1");
	}

}
