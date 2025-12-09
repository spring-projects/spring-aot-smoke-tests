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

package com.example.environment;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EnvironmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnvironmentApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(Label label, Environment environment) {
		return args -> {
			System.out.println("Label -> " + label.name());
			System.out.println("'test.property' -> " + environment.getProperty("test.property"));
		};
	}

	@Configuration(proxyBeanMethods = false)
	@Profile("default")
	static class DefaultConfiguration {

		@Bean
		Label label() {
			return new Label("default");
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Profile("prod")
	static class ProdConfiguration {

		@Bean
		Label label() {
			return new Label("prod");
		}

	}

	record Label(String name) {

	}

}
