package com.example.data.jpa;

import java.util.Optional;

import com.example.data.jpa.model.Author;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {

	Optional<Author> findByNameContainingIgnoreCase(String partialName);

	@Query("SELECT a FROM Author a WHERE a.name = :name")
	Optional<Author> queryFindByName(String name);

}
