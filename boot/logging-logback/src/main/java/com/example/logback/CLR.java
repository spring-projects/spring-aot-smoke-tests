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

package com.example.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(CLR.class);

	@Override
	public void run(String... args) throws Exception {
		LOGGER.trace("Trace message");
		LOGGER.debug("Debug message");
		LOGGER.info("Info message");
		LOGGER.warn("Warn message");
		LOGGER.error("Error message");

		LOGGER.info("Info with parameters: {}", 1);
		LOGGER.error("Error with stacktrace", new RuntimeException("Boom"));
	}

}
