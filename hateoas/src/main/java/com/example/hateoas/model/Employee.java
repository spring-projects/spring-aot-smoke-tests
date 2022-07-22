package com.example.hateoas.model;

import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "employee", collectionRelation = "employees")
public class Employee {

	private final String id;

	private final String firstName;

	private final String lastName;

	private final String managerId;

	public Employee(String id, String firstName, String lastName, String managerId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.managerId = managerId;
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

	public String getManagerId() {
		return managerId;
	}

}
