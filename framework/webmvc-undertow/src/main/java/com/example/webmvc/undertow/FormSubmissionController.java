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

package com.example.webmvc.undertow;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "form-submission", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormSubmissionController {

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public OutputForm greetingSubmit(@ModelAttribute InputForm form) {
		return new OutputForm(form.firstName, form.lastName);
	}

	record InputForm(String firstName, String lastName) {
	}

	record OutputForm(String firstName, String lastName) {
	}

}
