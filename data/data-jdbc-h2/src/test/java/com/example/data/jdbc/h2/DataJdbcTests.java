package com.example.data.jdbc.h2;

import java.util.Optional;
import java.util.Set;

import com.example.data.jdbc.h2.model.Author;
import com.example.data.jdbc.h2.model.Book;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJdbcTest
class DataJdbcTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	void shouldHaveSchema() {
		assertThatCode(() -> this.jdbcTemplate.execute("SELECT * FROM author")).doesNotThrowAnyException();
	}

	@Test
	void shouldSaveAndLoad() {
		Book book1 = new Book(null, "book-1");
		Author author1 = new Author(null, "author-1", Set.of(book1));
		this.authorRepository.save(author1);

		Optional<Author> found = this.authorRepository.queryFindByName("author-1");
		assertThat(found).isPresent();
		assertThat(found.get().getId()).isNotNull();
		assertThat(found.get().getName()).isEqualTo("author-1");
		assertThat(found.get().getBooks()).hasSize(1);
	}
}
