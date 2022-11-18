package com.example.configprops;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private String string;

	private DataSize dataSize;

	private List<String> stringList = new ArrayList<>();

	private List<Nested> nestedList = new ArrayList<>();

	private Nested nested = new Nested();

	@NestedConfigurationProperty
	private NestedNotInner nestedNotInner = new NestedNotInner();

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	public List<Nested> getNestedList() {
		return nestedList;
	}

	public void setNestedList(List<Nested> nestedList) {
		this.nestedList = nestedList;
	}

	public Nested getNested() {
		return nested;
	}

	public void setNested(Nested nested) {
		this.nested = nested;
	}

	public NestedNotInner getNestedNotInner() {
		return nestedNotInner;
	}

	public void setNestedNotInner(NestedNotInner nestedNotInner) {
		this.nestedNotInner = nestedNotInner;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public DataSize getDataSize() {
		return dataSize;
	}

	public void setDataSize(DataSize dataSize) {
		this.dataSize = dataSize;
	}

	@Override
	public String toString() {
		return "AppProperties{" + "string='" + string + '\'' + ", dataSize=" + dataSize + ", stringList=" + stringList
				+ ", nestedList=" + nestedList + ", nested=" + nested + ", nestedNotInner=" + nestedNotInner + '}';
	}

	public static class Nested {

		private int aInt;

		public int getaInt() {
			return aInt;
		}

		public void setaInt(int aInt) {
			this.aInt = aInt;
		}

		@Override
		public String toString() {
			return "Nested{" + "aInt=" + aInt + '}';
		}

	}

}
