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
