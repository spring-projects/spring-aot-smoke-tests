package com.example.data.jdbc.h2;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThatCode;

@DataJdbcTest
class DataJdbcTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void shouldHaveSchema() {
		assertThatCode(() -> this.jdbcTemplate.execute("SELECT * FROM author")).doesNotThrowAnyException();
	}

}
