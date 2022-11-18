package com.example.configprops;

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
