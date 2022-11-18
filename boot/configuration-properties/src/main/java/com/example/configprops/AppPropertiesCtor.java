package com.example.configprops;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "app.ctor")
public class AppPropertiesCtor {

	private final String string;

	private final DataSize dataSize;

	private final List<String> stringList;

	private final List<Nested> nestedList;

	private final Nested nested;

	private final NestedNotInner nestedNotInner;

	public AppPropertiesCtor(String string, DataSize dataSize, List<String> stringList, List<Nested> nestedList,
			Nested nested, NestedNotInner nestedNotInner) {
		this.string = string;
		this.dataSize = dataSize;
		this.stringList = stringList;
		this.nestedList = nestedList;
		this.nested = nested;
		this.nestedNotInner = nestedNotInner;
	}

	public String getString() {
		return string;
	}

	public DataSize getDataSize() {
		return dataSize;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public List<Nested> getNestedList() {
		return nestedList;
	}

	public Nested getNested() {
		return nested;
	}

	public NestedNotInner getNestedNotInner() {
		return nestedNotInner;
	}

	@Override
	public String toString() {
		return "AppPropertiesCtor{" + "string='" + string + '\'' + ", dataSize=" + dataSize + ", stringList="
				+ stringList + ", nestedList=" + nestedList + ", nested=" + nested + ", nestedNotInner="
				+ nestedNotInner + '}';
	}

	public static class Nested {

		private final int aInt;

		public Nested(int aInt) {
			this.aInt = aInt;
		}

		public int getaInt() {
			return aInt;
		}

		@Override
		public String toString() {
			return "Nested{" + "aInt=" + aInt + '}';
		}

	}

}
