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
