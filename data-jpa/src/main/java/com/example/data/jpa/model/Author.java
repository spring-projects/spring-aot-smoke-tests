package com.example.data.jpa.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Author {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Book> books;

	protected Author() {
	}

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

	public void setName(String name) {
		this.name = name;
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
