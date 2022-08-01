package com.example.webmvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "form-submission", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormSubmissionController {

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Form greetingSubmit(@ModelAttribute Form form) {
		return form;
	}

	record Form(String firstName, String lastName) {
	}

}
