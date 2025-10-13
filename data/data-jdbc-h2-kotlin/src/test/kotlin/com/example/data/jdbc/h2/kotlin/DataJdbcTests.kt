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

package com.example.data.jdbc.h2.kotlin

import com.example.data.jdbc.h2.kotlin.model.Author
import com.example.data.jdbc.h2.kotlin.model.Book
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest
import org.springframework.jdbc.core.JdbcTemplate
import java.util.Set

@DataJdbcTest
class DataJdbcTests {
	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var authorRepository: AuthorRepository

	@Test
	fun shouldHaveSchema() {
		Assertions.assertThatCode { jdbcTemplate.execute("SELECT * FROM author") }
			.doesNotThrowAnyException()
	}

	@Test
	fun shouldSaveAndLoad() {
		val book1 = Book(null, "book-1")
		val author1 = Author(null, "author-1", Set.of(book1))
		authorRepository.save(author1)
		val found = authorRepository.queryFindByName("author-1")
		assertThat(found).isNotNull()
		checkNotNull(found)
		assertThat(found.id).isNotNull()
		assertThat(found.name).isEqualTo("author-1")
		assertThat(found.books).hasSize(1)
	}
}
