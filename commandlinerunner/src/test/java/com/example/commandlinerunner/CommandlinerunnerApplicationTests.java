package com.example.commandlinerunner;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommandlinerunnerApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	CLR clr;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextLoads() {
		assertThat(this.applicationContext).as("ApplicationContextAware").isNotNull();
	}

	@Test
	void autowiringWorks() {
		assertThat(this.clr).as("@Autowired field").isNotNull();
	}

}
