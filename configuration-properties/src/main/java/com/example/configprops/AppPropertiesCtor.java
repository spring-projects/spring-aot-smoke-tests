package com.example.configprops;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "app.ctor")
class AppPropertiesCtor {

	private final String string;

	private final DataSize dataSize;

	private final List<String> stringList;

	private final List<Nested> nestedList;

	private final Nested nested;

	AppPropertiesCtor(String string, DataSize dataSize, List<String> stringList, List<Nested> nestedList,
			Nested nested) {
		this.string = string;
		this.dataSize = dataSize;
		this.stringList = stringList;
		this.nestedList = nestedList;
		this.nested = nested;
	}

	String getString() {
		return string;
	}

	DataSize getDataSize() {
		return dataSize;
	}

	List<String> getStringList() {
		return stringList;
	}

	List<Nested> getNestedList() {
		return nestedList;
	}

	Nested getNested() {
		return nested;
	}

	@Override
	public String toString() {
		return "AppPropertiesCtor{" + "string='" + string + '\'' + ", dataSize=" + dataSize + ", stringList="
				+ stringList + ", nestedList=" + nestedList + ", nested=" + nested + '}';
	}

	static class Nested {

		private final int aInt;

		Nested(int aInt) {
			this.aInt = aInt;
		}

		int getaInt() {
			return aInt;
		}

		@Override
		public String toString() {
			return "Nested{" + "aInt=" + aInt + '}';
		}

	}

}
