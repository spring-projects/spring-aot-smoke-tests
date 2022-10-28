package com.example.hateoas.model;

import java.util.Set;

import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "manager", collectionRelation = "managers")
public class Manager {

	private final String id;

	private final String firstName;

	private final String lastName;

	private final Set<String> reports;

	public Manager(String id, String firstName, String lastName, Set<String> reports) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.reports = reports;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<String> getReports() {
		return reports;
	}

}
