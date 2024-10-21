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
