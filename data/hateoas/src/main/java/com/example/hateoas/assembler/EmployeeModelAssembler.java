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
