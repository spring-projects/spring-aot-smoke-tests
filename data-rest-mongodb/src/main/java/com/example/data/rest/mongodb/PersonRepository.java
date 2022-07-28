package com.example.data.rest.mongodb;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "person")
public interface PersonRepository
		extends ListCrudRepository<Person, String>, ListPagingAndSortingRepository<Person, String> {

	List<Person> findByLastname(String lastname);

}
