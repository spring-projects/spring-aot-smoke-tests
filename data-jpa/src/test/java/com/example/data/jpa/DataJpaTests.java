package com.example.data.jpa;

import java.util.Optional;
import java.util.Set;

import com.example.data.jpa.model.Author;
import com.example.data.jpa.model.Book;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DataJpaTests {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	@Test
	void shouldInjectTestEntityManager() {
		assertThat(this.testEntityManager).as("testEntityManager").isNotNull();
	}

	@Test
	void shouldInjectJdbcTemplate() {
		assertThat(this.jdbcTemplate).as("jdbcTemplate").isNotNull();
	}

}
