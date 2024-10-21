/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
