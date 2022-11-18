package com.example.configprops.records;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "app.record")
public record AppPropertiesRecord(String string, DataSize dataSize, List<String> stringList, List<Nested> nestedList,
		Nested nested, @NestedConfigurationProperty NestedNotInner nestedNotInner) {
	public record Nested(int aInt) {
	}
}
