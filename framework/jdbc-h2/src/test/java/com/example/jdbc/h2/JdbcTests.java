package com.example.jdbc.h2;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestMethodOrder(OrderAnnotation.class)
class JdbcTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	@Order(0)
	void canInsert() {
		this.jdbcTemplate.execute("INSERT INTO authors (name) VALUES ('author-1')");
	}

	@Test
	@Order(1)
	void shouldBeIsolated() {
		Integer count = this.jdbcTemplate.queryForObject("SELECT COUNT(1) FROM authors", int.class);
		assertThat(count).isZero();
	}

}
