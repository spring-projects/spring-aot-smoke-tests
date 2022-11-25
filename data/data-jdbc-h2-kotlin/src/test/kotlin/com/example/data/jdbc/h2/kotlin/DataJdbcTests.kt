package com.example.data.jdbc.h2.kotlin

import com.example.data.jdbc.h2.kotlin.model.Author
import com.example.data.jdbc.h2.kotlin.model.Book
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
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
