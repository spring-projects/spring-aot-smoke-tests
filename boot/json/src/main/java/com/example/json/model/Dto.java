package com.example.json.model;

import java.util.Objects;

public class Dto {

	private String field;

	public Dto() {
	}

	public Dto(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Dto dto = (Dto) o;
		return Objects.equals(field, dto.field);
	}

	@Override
	public int hashCode() {
		return Objects.hash(field);
	}

}
