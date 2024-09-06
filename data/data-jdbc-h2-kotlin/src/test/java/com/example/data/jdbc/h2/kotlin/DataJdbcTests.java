package com.example.data.jdbc.h2.kotlin;

import com.example.data.jdbc.h2.kotlin.model.Author;
import com.example.data.jdbc.h2.kotlin.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Set;

@DataJdbcTest
class DataJdbcTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	void shouldHaveSchema() {
		Assertions.assertThatNoException().isThrownBy(() -> jdbcTemplate.execute("SELECT * FROM author"));
	}

	@Test
	void shouldSaveAndLoad() {

		var book1 = new Book(null, "book-1");
		var author1 = new Author(null, "author-1", Set.of(book1));
		authorRepository.save(author1);
		var found = authorRepository.queryFindByName("author-1");
		Assertions.assertThat(found).isNotNull();
		Assertions.assertThat(found.getId()).isNotNull();
		Assertions.assertThat(found.getName()).isEqualTo("author-1");
		Assertions.assertThat(found.getBooks()).hasSize(1);
	}

}
