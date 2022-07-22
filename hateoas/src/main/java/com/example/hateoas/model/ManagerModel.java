package com.example.hateoas.model;

import org.springframework.hateoas.EntityModel;

public class ManagerModel extends EntityModel<Manager> {

	public ManagerModel(Manager manager) {
		super(manager);
	}

}
