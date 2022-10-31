package com.example.json.model;

import java.util.Objects;

public class Dto3 {

	private String field;

	public Dto3() {
	}

	public Dto3(String field) {
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
		Dto3 dto3 = (Dto3) o;
		return Objects.equals(field, dto3.field);
	}

	@Override
	public int hashCode() {
		return Objects.hash(field);
	}

}
