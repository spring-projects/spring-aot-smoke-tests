/*
 * Copyright 2022-2024 the original author or authors.
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

package com.example.configprops;

import com.example.configprops.ctor.AppPropertiesCtor;
import com.example.configprops.javabean.AppProperties;
import com.example.configprops.records.AppPropertiesRecord;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties({ AppProperties.class, AppPropertiesCtor.class, AppPropertiesRecord.class })
public class CLR implements CommandLineRunner {

	private final AppProperties appProperties;

	private final AppPropertiesCtor appPropertiesCtor;

	private final AppPropertiesRecord appPropertiesRecord;

	private final Environment environment;

	CLR(AppProperties appProperties, AppPropertiesCtor appPropertiesCtor, AppPropertiesRecord appPropertiesRecord,
			Environment environment) {
		this.appProperties = appProperties;
		this.appPropertiesCtor = appPropertiesCtor;
		this.appPropertiesRecord = appPropertiesRecord;
		this.environment = environment;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf("appProperties.getString(): %s%n", appProperties.getString());
		System.out.printf("appProperties.getDataSize(): %s%n", appProperties.getDataSize());
		System.out.printf("appProperties.getStringList(): %s%n", appProperties.getStringList());
		System.out.printf("appProperties.getNestedList(): %s%n", appProperties.getNestedList());
		System.out.printf("appProperties.getStringMap(): %s%n", appProperties.getStringMap());
		System.out.printf("appProperties.getNestedMap(): %s%n", appProperties.getNestedMap());
		System.out.printf("appProperties.getNested(): %s%n", appProperties.getNested());
		System.out.printf("appProperties.getNested().getaInt(): %s%n", appProperties.getNested().getaInt());
		System.out.printf("appProperties.getNestedNotInner(): %s%n", appProperties.getNestedNotInner());
		System.out.printf("appProperties.getNestedNotInner().getaInt(): %s%n",
				appProperties.getNestedNotInner().getaInt());

		System.out.printf("appPropertiesCtor.getString(): %s%n", appPropertiesCtor.getString());
		System.out.printf("appPropertiesCtor.getDataSize(): %s%n", appPropertiesCtor.getDataSize());
		System.out.printf("appPropertiesCtor.getStringList(): %s%n", appPropertiesCtor.getStringList());
		System.out.printf("appPropertiesCtor.getNestedList(): %s%n", appPropertiesCtor.getNestedList());
		System.out.printf("appPropertiesCtor.getNested(): %s%n", appPropertiesCtor.getNested());
		System.out.printf("appPropertiesCtor.getNested().getaInt(): %s%n", appPropertiesCtor.getNested().getaInt());
		System.out.printf("appPropertiesCtor.getNestedNotInner(): %s%n", appPropertiesCtor.getNestedNotInner());
		System.out.printf("appPropertiesCtor.getNestedNotInner().getaInt(): %s%n",
				appPropertiesCtor.getNestedNotInner().getaInt());

		System.out.printf("appPropertiesRecord.string(): %s%n", appPropertiesRecord.string());
		System.out.printf("appPropertiesRecord.dataSize(): %s%n", appPropertiesRecord.dataSize());
		System.out.printf("appPropertiesRecord.stringList(): %s%n", appPropertiesRecord.stringList());
		System.out.printf("appPropertiesRecord.nestedList(): %s%n", appPropertiesRecord.nestedList());
		System.out.printf("appPropertiesRecord.nested(): %s%n", appPropertiesRecord.nested());
		System.out.printf("appPropertiesRecord.nested().aInt(): %s%n", appPropertiesRecord.nested().aInt());
		System.out.printf("appPropertiesRecord.nestedNotInner(): %s%n", appPropertiesRecord.nestedNotInner());
		System.out.printf("appPropertiesRecord.nestedNotInner().getaInt(): %s%n",
				appPropertiesRecord.nestedNotInner().getaInt());

		System.out.printf("test.yaml some.imported.key: %s%n", environment.getProperty("some.imported.key"));
	}

}
