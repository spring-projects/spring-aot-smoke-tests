package com.example.webmvc.jetty;

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
