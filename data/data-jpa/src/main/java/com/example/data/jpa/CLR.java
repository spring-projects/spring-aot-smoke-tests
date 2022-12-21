package com.example.data.jpa;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.example.data.jpa.model.Author;
import com.example.data.jpa.model.Book;

import com.example.data.jpa.model.Publisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class CLR implements CommandLineRunner {

	private final AuthorRepository authorRepository;

	private final BookRepository bookRepository;

	private final PublisherRepository publisherRepository;

	CLR(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.publisherRepository = publisherRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		var authors = insertAuthors();
		listAllAuthors();
		findById(authors);
		findByPartialName();
		queryFindByName();
		streamAuthors();
		deleteAll();
		entityGraph();
		insertPublishers(); // uses AbstractPersistable
	}

	private void deleteAll() {
		this.authorRepository.deleteAll();
		long count = this.authorRepository.count();
		System.out.printf("deleteAll(): count = %d%n", count);
	}

	private void queryFindByName() {
		Author author1 = this.authorRepository.queryFindByName("Josh Long").orElse(null);
		Author author2 = this.authorRepository.queryFindByName("Martin Kleppmann").orElse(null);

		System.out.printf("queryFindByName(): author1 = %s%n", author1);
		System.out.printf("queryFindByName(): author2 = %s%n", author2);
	}

	private void findByPartialName() {
		Author author1 = this.authorRepository.findByNameContainingIgnoreCase("sh lo").orElse(null);
		Author author2 = this.authorRepository.findByNameContainingIgnoreCase("in kl").orElse(null);

		System.out.printf("findByPartialName(): author1 = %s%n", author1);
		System.out.printf("findByPartialName(): author2 = %s%n", author2);
	}

	private void findById(List<Author> authors) {
		Author author1 = this.authorRepository.findById(authors.get(0).getId()).orElse(null);
		Author author2 = this.authorRepository.findById(authors.get(1).getId()).orElse(null);

		System.out.printf("findById(): author1 = %s%n", author1);
		System.out.printf("findById(): author2 = %s%n", author2);
	}

	private void entityGraph() {

		Book book = new Book(null, "Spring in Action");
		Author author = new Author(null, "Craig Walls", Collections.emptySet());
		book.getAuthors().add(author);
		bookRepository.save(book);

		Book loaded = bookRepository.findByTitleWithAdHocGraph("Spring in Action");
		System.out.println("namedEntityGraph: " + loaded);
	}

	private void listAllAuthors() {
		List<Author> authors = this.authorRepository.findAll();
		for (Author author : authors) {
			System.out.printf("listAllAuthors(): author = %s%n", author);
			for (Book book : author.getBooks()) {
				System.out.printf("\t%s%n", book);
			}
		}
	}

	private void streamAuthors() {

		Stream<Author> authors = this.authorRepository.findByNameContaining("Martin");
		authors.forEach(author -> System.out.printf("streamAuthors(): author = %s%n", author));
	}

	private List<Author> insertAuthors() {
		Author author1 = this.authorRepository.save(new Author(null, "Josh Long",
				Set.of(new Book(null, "Reactive Spring"), new Book(null, "Cloud Native Java"))));
		Author author2 = this.authorRepository.save(
				new Author(null, "Martin Kleppmann", Set.of(new Book(null, "Designing Data Intensive Applications"))));

		System.out.printf("insertAuthors(): author1 = %s%n", author1);
		System.out.printf("insertAuthors(): author2 = %s%n", author2);

		return Arrays.asList(author1, author2);
	}

	private List<Publisher> insertPublishers() {
		Publisher publisher1 = this.publisherRepository.save(new Publisher("independently published"));
		System.out.printf("insertPublishers(): publisher1 - %s%n", publisher1);
		return List.of(publisher1);
	}

}
