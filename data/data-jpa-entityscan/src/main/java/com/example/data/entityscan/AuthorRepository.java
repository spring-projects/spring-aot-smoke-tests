package com.example.data.entityscan;

import entity.Author;

import org.springframework.data.repository.ListCrudRepository;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {

}
