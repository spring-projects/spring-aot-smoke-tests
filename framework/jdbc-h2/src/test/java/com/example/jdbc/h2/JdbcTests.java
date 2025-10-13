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

package com.example.jdbc.h2;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
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
