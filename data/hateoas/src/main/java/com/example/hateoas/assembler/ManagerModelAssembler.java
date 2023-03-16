package com.example.hateoas.assembler;

import com.example.hateoas.ManagerController;
import com.example.hateoas.model.Manager;
import com.example.hateoas.model.ManagerModel;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ManagerModelAssembler extends RepresentationModelAssemblerSupport<Manager, ManagerModel> {

	public ManagerModelAssembler() {
		super(ManagerController.class, ManagerModel.class);
	}

	@Override
	public ManagerModel toModel(Manager entity) {
		ManagerModel model = createModelWithId(entity.getId(), entity);
		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ManagerController.class).reports(entity.getId()))
			.withRel("reports"));
		return model;
	}

	@Override
	protected ManagerModel instantiateModel(Manager entity) {
		return new ManagerModel(entity);
	}

}
