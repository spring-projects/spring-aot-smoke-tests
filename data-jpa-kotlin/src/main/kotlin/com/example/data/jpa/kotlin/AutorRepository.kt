package com.example.data.jpa.kotlin

import com.example.data.jpa.kotlin.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AuthorRepository : JpaRepository<Author, Long> {

	fun findByNameContainingIgnoreCase(partialName: String): Author?

	@Query("SELECT a FROM Author a WHERE a.name = :name")
	fun queryFindByName(name: String): Author?
}
