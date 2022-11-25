package com.example.data.jdbc.h2.kotlin

import com.example.data.jdbc.h2.kotlin.model.Author
import com.example.data.jdbc.h2.kotlin.model.Book
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CLR(private val authorRepository: AuthorRepository) : CommandLineRunner {
	override fun run(vararg args: String) {
		insertAuthors()
		listAllAuthors()
		findById()
		findByPartialName()
		queryFindByName()
		deleteAll()
	}

	private fun deleteAll() {
		authorRepository.deleteAll()
		val count = authorRepository.count()
		System.out.printf("deleteAll(): count = %d%n", count)
	}

	private fun queryFindByName() {
		val author1 = authorRepository.queryFindByName("Josh Long")
		val author2 = authorRepository.queryFindByName("Martin Kleppmann")
		System.out.printf("queryFindByName(): author1 = %s%n", author1)
		System.out.printf("queryFindByName(): author2 = %s%n", author2)
	}

	private fun findByPartialName() {
		val author1 =
			authorRepository.findByNameContainingIgnoreCase("sh lo")
		val author2 =
			authorRepository.findByNameContainingIgnoreCase("in kl")
		System.out.printf("findByPartialName(): author1 = %s%n", author1)
		System.out.printf("findByPartialName(): author2 = %s%n", author2)
	}

	private fun findById() {
		val author1 = authorRepository.findById(1L).orElse(null)
		val author2 = authorRepository.findById(2L).orElse(null)
		System.out.printf("findById(): author1 = %s%n", author1)
		System.out.printf("findById(): author2 = %s%n", author2)
	}

	private fun listAllAuthors() {
		val authors = authorRepository.findAll()
		for (author in authors) {
			System.out.printf("listAllAuthors(): author = %s%n", author)
			for (book in author.books) {
				System.out.printf("\t%s%n", book)
			}
		}
	}

	private fun insertAuthors() {
		val author1 = authorRepository.save(
			Author(
				null, "Josh Long",
				setOf(Book(null, "Reactive Spring"), Book(null, "Cloud Native Java"))
			)
		)
		val author2 = authorRepository.save(
			Author(
				null,
				"Martin Kleppmann",
				setOf(Book(null, "Designing Data Intensive Applications"))
			)
		)
		System.out.printf("insertAuthors(): author1 = %s%n", author1)
		System.out.printf("insertAuthors(): author2 = %s%n", author2)
	}
}
