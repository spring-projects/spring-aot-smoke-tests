package com.example.jdbc.mariadb;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CLR implements CommandLineRunner {

	private final JdbcTemplate jdbcTemplate;

	public CLR(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) {
		insertAuthors();
		queryAuthors();
	}

	private void queryAuthors() {
		List<Author> authors = jdbcTemplate.query("SELECT * FROM authors", Author.Mapper.INSTANCE);
		for (Author author : authors) {
			System.out.printf("Found author: %s%n", author);
		}
	}

	private void insertAuthors() {
		List<String> authors = List.of("mbhave", "snicoll", "wilkinsona");

		for (String author : authors) {
			this.jdbcTemplate.update("INSERT INTO authors (name) VALUES (?)", author);
		}
	}

}
