package com.example.data.jdbc.h2.kotlin

import com.example.data.jdbc.h2.kotlin.model.Author
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface AuthorRepository : ListCrudRepository<Author, Long> {
	fun findByNameContainingIgnoreCase(partialName: String): Author?

	@Query("SELECT * FROM author a WHERE a.name = :name LIMIT 1")
	fun queryFindByName(name: String): Author?
}
