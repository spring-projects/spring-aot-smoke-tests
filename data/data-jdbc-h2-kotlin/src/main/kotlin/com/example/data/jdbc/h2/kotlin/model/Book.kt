package com.example.data.jdbc.h2.kotlin.model

import org.springframework.data.annotation.Id

data class Book(
	@Id val id: Long?,
	val title: String
) {
	override fun toString(): String {
		return "Book(title='$title')"
	}
}
