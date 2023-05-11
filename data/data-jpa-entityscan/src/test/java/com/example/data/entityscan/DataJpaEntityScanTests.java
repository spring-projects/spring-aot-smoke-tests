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
