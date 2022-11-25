package com.example.logback.xml;

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
