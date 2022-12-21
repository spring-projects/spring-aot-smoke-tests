package com.example.data.jpa;

import com.example.data.jpa.model.Publisher;
import org.springframework.data.repository.ListCrudRepository;

public interface PublisherRepository extends ListCrudRepository<Publisher, Long> {

}
