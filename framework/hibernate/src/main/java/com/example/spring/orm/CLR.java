package com.example.spring.orm;

import java.util.Collections;
import java.util.List;

import com.example.spring.orm.model.Author;
import com.example.spring.orm.model.Book;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CLR implements CommandLineRunner {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void run(String... args) {
		var books = insertBooks();
		entityGraph(books.iterator().next().getId());
	}

	public void entityGraph(Long id) {

		EntityGraph<?> entityGraph = entityManager.getEntityGraph("Book.authors");

		System.out.println("entityGraph: " + entityGraph);

		Book result = entityManager.find(Book.class, id,
				Collections.singletonMap("jakarta.persistence.fetchgraph", entityGraph));
		System.out.println("namedEntityGraph: " + result);
	}

	public List<Book> insertBooks() {

		Book book = new Book(null, "Spring in Action");
		Author author = new Author(null, "Craig Walls", Collections.emptySet());
		book.getAuthors().add(author);

		entityManager.persist(book);
		System.out.println("book: " + book);

		entityManager.flush();
		entityManager.getEntityManagerFactory().getCache().evictAll();

		Session session = entityManager.unwrap(Session.class);

		if (session != null) {
			session.clear(); // internal cache clear
		}

		return Collections.singletonList(book);
	}

}
