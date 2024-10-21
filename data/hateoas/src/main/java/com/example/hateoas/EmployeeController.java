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

import com.example.hateoas.assembler.EmployeeModelAssembler;
import com.example.hateoas.model.Employee;
import com.example.hateoas.model.EmployeeModel;

import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Employee.class)
public class EmployeeController {

	private final EmployeeModelAssembler employeeModelAssembler;

	public EmployeeController(EmployeeModelAssembler employeeModelAssembler) {
		this.employeeModelAssembler = employeeModelAssembler;
	}

	@GetMapping("{id}")
	public EmployeeModel getById(@PathVariable String id) {
		Employee employee = new Employee(id, "first-name", "last-name", id);
		return employeeModelAssembler.toModel(employee);
	}

}
