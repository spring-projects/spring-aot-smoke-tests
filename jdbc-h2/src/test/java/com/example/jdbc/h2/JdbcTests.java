package com.example.jdbc.h2;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThatCode;

@JdbcTest
class JdbcTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void shouldHaveSchema() {
		assertThatCode(() -> this.jdbcTemplate.execute("SELECT * FROM authors")).doesNotThrowAnyException();
	}

}
