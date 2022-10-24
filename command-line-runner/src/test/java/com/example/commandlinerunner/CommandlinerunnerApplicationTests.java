package com.example.commandlinerunner;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, useMainMethod = UseMainMethod.ALWAYS, args = "--test.property=1")
class CommandlinerunnerApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	private CLR clr;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextIsInjected() {
		assertThat(this.applicationContext).as("ApplicationContextAware").isNotNull();
	}

	@Test
	void autowiringWorks() {
		assertThat(this.clr).as("@Autowired field").isNotNull();
	}

	@Test
	void mainShouldRun() {
		String property = System.getProperty("main.ran", "false");
		assertThat(property).isEqualTo("true");
	}

	@Test
	void shouldPassArgs(@Autowired ApplicationArguments applicationArguments) {
		List<String> values = applicationArguments.getOptionValues("test.property");
		assertThat(values).containsExactly("1");
	}

}
