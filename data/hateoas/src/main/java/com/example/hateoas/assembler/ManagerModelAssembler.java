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
