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

package com.example.json.model;

import java.util.Objects;

public class Dto2 {

	private final String field;

	public Dto2(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Dto2 dto2 = (Dto2) o;
		return Objects.equals(field, dto2.field);
	}

	@Override
	public int hashCode() {
		return Objects.hash(field);
	}

}
