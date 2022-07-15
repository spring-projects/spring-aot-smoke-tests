package com.example.data.jpa.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Book {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	protected Book() {
	}

	public Book(Long id, String title) {
		this.id = id;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Book book = (Book) o;
		return Objects.equals(id, book.id) && Objects.equals(title, book.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}

	@Override
	public String toString() {
		return "Book{" + "title='" + title + '\'' + '}';
	}

}
