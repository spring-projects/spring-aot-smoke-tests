package com.example.configprops;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "app")
class AppProperties {

	private String string;

	private DataSize dataSize;

	private List<String> stringList = new ArrayList<>();

	private List<Nested> nestedList = new ArrayList<>();

	private Nested nested = new Nested();

	List<String> getStringList() {
		return stringList;
	}

	void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	List<Nested> getNestedList() {
		return nestedList;
	}

	void setNestedList(List<Nested> nestedList) {
		this.nestedList = nestedList;
	}

	Nested getNested() {
		return nested;
	}

	void setNested(Nested nested) {
		this.nested = nested;
	}

	String getString() {
		return string;
	}

	void setString(String string) {
		this.string = string;
	}

	DataSize getDataSize() {
		return dataSize;
	}

	void setDataSize(DataSize dataSize) {
		this.dataSize = dataSize;
	}

	static class Nested {

		private int aInt;

		int getaInt() {
			return aInt;
		}

		void setaInt(int aInt) {
			this.aInt = aInt;
		}

		@Override
		public String toString() {
			return "Nested{" + "aInt=" + aInt + '}';
		}

	}

	@Override
	public String toString() {
		return "AppProperties{" + "string='" + string + '\'' + ", dataSize=" + dataSize + ", stringList=" + stringList
				+ ", nestedList=" + nestedList + ", nested=" + nested + '}';
	}

}
