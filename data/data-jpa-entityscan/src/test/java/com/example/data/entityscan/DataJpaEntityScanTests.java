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

package com.example.data.entityscan;

import java.util.Optional;

import entity.Author;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DataJpaConfiguration.class)
class DataJpaEntityScanTests {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void shouldSaveAndLoad() {
		Author author1 = new Author(null, "author-1");
		this.authorRepository.save(author1);

		Optional<Author> found = this.authorRepository.findById(author1.getId());
		assertThat(found).isPresent();
		assertThat(found.get().getId()).isNotNull();
		assertThat(found.get().getName()).isEqualTo("author-1");
	}

	@Test
	void shouldInjectTestEntityManager() {
		assertThat(this.testEntityManager).as("testEntityManager").isNotNull();
	}

	@Test
	void shouldInjectJdbcTemplate() {
		assertThat(this.jdbcTemplate).as("jdbcTemplate").isNotNull();
	}

}
