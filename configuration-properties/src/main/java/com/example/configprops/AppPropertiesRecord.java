package com.example.configprops;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "app.record")
public record AppPropertiesRecord(String string, DataSize dataSize, List<String> stringList, List<Nested> nestedList,
		Nested nested) {
	public record Nested(int aInt) {
	}
}
