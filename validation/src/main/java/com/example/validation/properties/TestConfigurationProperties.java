package com.example.validation.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "test")
@Validated
class TestConfigurationProperties {

	@NotBlank
	private String field;

	@Valid
	private Nested nested = new Nested();

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Nested getNested() {
		return nested;
	}

	static class Nested {

		@NotBlank
		private String field;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

	}

}
