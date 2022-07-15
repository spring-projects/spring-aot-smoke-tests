package com.example.data.jdbc.model;

import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class Author {

	@Id
	private final Long id;

	private final String name;

	private final Set<Book> books;

	public Author(Long id, String name, Set<Book> books) {
		this.id = id;
		this.name = name;
		this.books = books;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Author author = (Author) o;
		return Objects.equals(id, author.id) && Objects.equals(name, author.name)
				&& Objects.equals(books, author.books);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, books);
	}

	@Override
	public String toString() {
		return "Author{" + "name='" + name + '\'' + '}';
	}

}
