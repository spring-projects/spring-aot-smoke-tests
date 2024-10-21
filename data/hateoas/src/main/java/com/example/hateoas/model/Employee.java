/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
