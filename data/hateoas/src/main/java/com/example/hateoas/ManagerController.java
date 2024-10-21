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

package com.example.hateoas;

import java.util.List;
import java.util.Set;

import com.example.hateoas.assembler.EmployeeModelAssembler;
import com.example.hateoas.assembler.ManagerModelAssembler;
import com.example.hateoas.model.Employee;
import com.example.hateoas.model.EmployeeModel;
import com.example.hateoas.model.Manager;
import com.example.hateoas.model.ManagerModel;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/manager", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Manager.class)
public class ManagerController {

	private final EmployeeModelAssembler employeeModelAssembler;

	private final ManagerModelAssembler managerModelAssembler;

	ManagerController(EmployeeModelAssembler employeeModelAssembler, ManagerModelAssembler managerModelAssembler) {
		this.employeeModelAssembler = employeeModelAssembler;
		this.managerModelAssembler = managerModelAssembler;
	}

	@GetMapping("{id}")
	public ManagerModel getById(@PathVariable String id) {
		Manager manager = new Manager(id, "first-name", "last-name", Set.of("1", "2", "3"));
		return this.managerModelAssembler.toModel(manager);
	}

	@GetMapping("{id}/reports")
	public CollectionModel<EmployeeModel> reports(@PathVariable String id) {
		List<Employee> employees = List.of(new Employee("1", "first-name-1", "last-name-1", "1"),
				new Employee("2", "first-name-2", "last-name-2", "1"),
				new Employee("3", "first-name-3", "last-name-3", "1"));
		return employeeModelAssembler.toCollectionModel(employees)
			.withFallbackType(EmployeeModel.class)
			.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ManagerController.class).reports(id))
				.withSelfRel());
	}

}
