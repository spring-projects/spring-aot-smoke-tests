package com.example.data.jdbc.h2.kotlin.model

import org.springframework.data.annotation.Id

data class Author(
	@Id val id: Long?,
	val name: String,
	val books: Set<Book>
) {
	override fun toString(): String {
		return "Author(name='$name')"
	}
}
