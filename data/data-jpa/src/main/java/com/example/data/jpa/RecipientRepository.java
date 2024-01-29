package com.example.data.jpa;

import com.example.data.jpa.model.Recipient;

import org.springframework.data.repository.ListCrudRepository;

interface RecipientRepository extends ListCrudRepository<Recipient, Long> {

}
