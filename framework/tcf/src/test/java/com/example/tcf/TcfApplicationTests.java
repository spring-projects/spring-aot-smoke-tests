package com.example.tcf;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(MyServiceConfiguration.class)
class TcfApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	private MyService myService;

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
		assertThat(this.myService).as("@Autowired field").isNotNull();
		assertThat(this.myService).isInstanceOf(MyServiceImpl.class);
	}

}
