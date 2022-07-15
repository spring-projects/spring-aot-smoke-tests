package com.example.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void run(String... args) throws Exception {
		LOGGER.trace("Trace message");
		LOGGER.debug("Debug message");
		LOGGER.info("Info message");
		LOGGER.warn("Warn message");
		LOGGER.error("Error message");
		LOGGER.fatal("Fatal message");

		LOGGER.info("Info with parameters: {}", 1);
		LOGGER.error("Error with stacktrace", new RuntimeException("Boom"));
	}

}
