package com.example.data.jpa.kotlin.model

import jakarta.persistence.*
import java.util.*

@Entity
class Author {

	@Id
	@GeneratedValue
	var id: Long? = null
	var name: String? = null

	@OneToMany(cascade = [CascadeType.ALL])
	var books: Set<Book>? = null

	protected constructor() {}

	constructor(id: Long?, name: String?, books: Set<Book>?) {
		this.id = id
		this.name = name
		this.books = books
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}
		if (other == null) {
			return false
		}
		val author = other as Author
		return id == author.id && name == author.name && books == author.books
	}

	override fun hashCode(): Int {
		return Objects.hash(id, name, books)
	}

	override fun toString(): String {
		return "Author{name='$name'}"
	}
}
