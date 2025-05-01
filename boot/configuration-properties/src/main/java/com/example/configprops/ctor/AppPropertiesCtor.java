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

package com.example.configprops.ctor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@ConfigurationProperties(prefix = "app.ctor")
public class AppPropertiesCtor {

	private final String string;

	private final DataSize dataSize;

	private final DataSize customDefaultUnitDataSize;

	private final Duration duration;

	private final Duration customDefaultUnitDuration;

	private final List<String> stringList;

	private final List<Nested> nestedList;

	private final Nested nested;

	@NestedConfigurationProperty
	private final NestedNotInner nestedNotInner;

	public AppPropertiesCtor(String string, DataSize dataSize,
			@DataSizeUnit(DataUnit.MEGABYTES) DataSize customDefaultUnitDataSize, Duration duration,
			@DurationUnit(ChronoUnit.MINUTES) Duration customDefaultUnitDuration, List<String> stringList,
			List<Nested> nestedList, Nested nested, NestedNotInner nestedNotInner) {
		this.string = string;
		this.dataSize = dataSize;
		this.customDefaultUnitDataSize = customDefaultUnitDataSize;
		this.duration = duration;
		this.customDefaultUnitDuration = customDefaultUnitDuration;
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

	public DataSize getCustomDefaultUnitDataSize() {
		return customDefaultUnitDataSize;
	}

	public Duration getDuration() {
		return duration;
	}

	public Duration getCustomDefaultUnitDuration() {
		return customDefaultUnitDuration;
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
		return "AppPropertiesCtor{" + "string='" + string + '\'' + ", dataSize=" + dataSize
				+ ", customDefaultUnitDataSize=" + customDefaultUnitDataSize + ", duration=" + duration
				+ ", customDefaultUnitDuration=" + customDefaultUnitDuration + ", stringList=" + stringList
				+ ", nestedList=" + nestedList + ", nested=" + nested + ", nestedNotInner=" + nestedNotInner + '}';
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
