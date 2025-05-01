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

package com.example.configprops.records;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@ConfigurationProperties(prefix = "app.record")
public record AppPropertiesRecord(String string, DataSize dataSize,
		@DataSizeUnit(DataUnit.MEGABYTES) DataSize customDefaultUnitDataSize, Duration duration,
		@DurationUnit(ChronoUnit.MINUTES) Duration customDefaultUnitDuration, List<String> stringList,
		List<Nested> nestedList, Nested nested, @NestedConfigurationProperty NestedNotInner nestedNotInner) {
	public record Nested(int aInt) {
	}
}
