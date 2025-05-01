/*
 * Copyright 2022-2025 the original author or authors.
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

package com.example.configprops.javabean;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private String string;

	private DataSize dataSize;

	@DataSizeUnit(DataUnit.MEGABYTES)
	private DataSize customDefaultUnitDataSize;

	private Duration duration;

	@DurationUnit(ChronoUnit.MINUTES)
	private Duration customDefaultUnitDuration;

	private List<String> stringList = new ArrayList<>();

	private List<Nested> nestedList = new ArrayList<>();

	private Map<String, String> stringMap = new LinkedHashMap<>();

	private Map<String, Nested> nestedMap = new LinkedHashMap<>();

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

	public Map<String, String> getStringMap() {
		return this.stringMap;
	}

	public void setStringMap(Map<String, String> stringMap) {
		this.stringMap = stringMap;
	}

	public Map<String, Nested> getNestedMap() {
		return this.nestedMap;
	}

	public void setNestedMap(Map<String, Nested> nestedMap) {
		this.nestedMap = nestedMap;
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

	public DataSize getCustomDefaultUnitDataSize() {
		return this.customDefaultUnitDataSize;
	}

	public void setCustomDefaultUnitDataSize(DataSize customDefaultUnitDataSize) {
		this.customDefaultUnitDataSize = customDefaultUnitDataSize;
	}

	public Duration getDuration() {
		return this.duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Duration getCustomDefaultUnitDuration() {
		return this.customDefaultUnitDuration;
	}

	public void setCustomDefaultUnitDuration(Duration customDefaultUnitDuration) {
		this.customDefaultUnitDuration = customDefaultUnitDuration;
	}

	@Override
	public String toString() {
		return "AppProperties{" + "string='" + string + '\'' + ", dataSize=" + dataSize + ", customDefaultUnitDataSize="
				+ customDefaultUnitDataSize + ", duration=" + duration + ", customDefaultUnitDuration="
				+ customDefaultUnitDuration + ", stringList=" + stringList + ", nestedList=" + nestedList + ", nested="
				+ nested + ", nestedNotInner=" + nestedNotInner + '}';
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
