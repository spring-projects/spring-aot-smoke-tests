package com.example.hateoas.model;

import org.springframework.hateoas.EntityModel;

public class EmployeeModel extends EntityModel<Employee> {

	public EmployeeModel(Employee employee) {
		super(employee);
	}

}
