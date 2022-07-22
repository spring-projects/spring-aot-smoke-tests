package com.example.hateoas.assembler;

import com.example.hateoas.EmployeeController;
import com.example.hateoas.model.Employee;
import com.example.hateoas.model.EmployeeModel;
import com.example.hateoas.model.Manager;

import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EmployeeModelAssembler extends RepresentationModelAssemblerSupport<Employee, EmployeeModel> {

	private final EntityLinks entityLinks;

	EmployeeModelAssembler(EntityLinks entityLinks) {
		super(EmployeeController.class, EmployeeModel.class);
		this.entityLinks = entityLinks;
	}

	@Override
	protected EmployeeModel instantiateModel(Employee entity) {
		return new EmployeeModel(entity);
	}

	@Override
	public EmployeeModel toModel(Employee entity) {
		EmployeeModel model = createModelWithId(entity.getId(), entity);
		model.add(this.entityLinks.linkToItemResource(Manager.class, entity.getManagerId()).withRel("manager"));
		return model;
	}

}
