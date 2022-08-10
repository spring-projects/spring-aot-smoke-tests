package com.example.data.jdbc;

import java.util.Optional;

import com.example.data.jdbc.model.Author;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {

	Optional<Author> findByNameContainingIgnoreCase(String partialName);

	@Query("SELECT * FROM author a WHERE a.name = :name LIMIT 1")
	Optional<Author> queryFindByName(String name);

}
