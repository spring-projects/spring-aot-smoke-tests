package com.example.data.entityscan;

import java.util.Arrays;
import java.util.List;

import entity.Author;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class CLR implements CommandLineRunner {

	private final AuthorRepository authorRepository;

	CLR(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		List<Author> authors = insertAuthors();
		listAllAuthors();
		findById(authors);
		deleteAll();
	}

	private void deleteAll() {
		this.authorRepository.deleteAll();
		long count = this.authorRepository.count();
		System.out.printf("deleteAll(): count = %d%n", count);
	}

	private void findById(List<Author> authors) {
		Author author1 = this.authorRepository.findById(authors.get(0).getId()).orElse(null);
		Author author2 = this.authorRepository.findById(authors.get(1).getId()).orElse(null);

		System.out.printf("findById(): author1 = %s%n", author1);
		System.out.printf("findById(): author2 = %s%n", author2);
	}

	private void listAllAuthors() {
		List<Author> authors = this.authorRepository.findAll();
		for (Author author : authors) {
			System.out.printf("listAllAuthors(): author = %s%n", author);
		}
	}

	private List<Author> insertAuthors() {
		Author author1 = this.authorRepository.save(new Author(null, "Josh Long"));
		Author author2 = this.authorRepository.save(new Author(null, "Martin Kleppmann"));

		System.out.printf("insertAuthors(): author1 = %s%n", author1);
		System.out.printf("insertAuthors(): author2 = %s%n", author2);

		return Arrays.asList(author1, author2);
	}

}
