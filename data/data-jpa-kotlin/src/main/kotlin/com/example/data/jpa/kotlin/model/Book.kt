package com.example.data.jpa.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class Book(@Id @GeneratedValue var id: Long?, var title: String?) {

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}
		if (other == null) {
			return false
		}
		val book = other as Book
		return id == book.id && title == book.title
	}

	override fun hashCode(): Int {
		return Objects.hash(id, title)
	}

	override fun toString(): String {
		return "Book{title='$title'}"
	}
}
