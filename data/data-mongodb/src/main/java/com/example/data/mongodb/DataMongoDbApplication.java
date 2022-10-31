package com.example.data.mongodb;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoManagedTypes;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@SpringBootApplication
@EnableMongoAuditing(auditorAwareRef = "fixedAuditor")
public class DataMongoDbApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataMongoDbApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	AuditorAware<String> fixedAuditor() {
		return () -> Optional.of("Douglas Adams");
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {

		return new MongoCustomConversions(Arrays.asList(new StringToNameConverter(), new NameToStringConverter()));
	}

	@Bean
	public MongoManagedTypes managedTypes() {
		return MongoManagedTypes.from(Customer.class, Order.class);
	}

	@ReadingConverter
	public static class StringToNameConverter implements Converter<String, Name> {

		@Override
		public Name convert(String source) {

			String[] args = source.split(";");
			return new Name(args[0].trim(), args[1].trim());
		}

	}

	@WritingConverter
	public static class NameToStringConverter implements Converter<Name, String> {

		@Override
		public String convert(Name source) {
			return source.getFirstname() + "; " + source.getLastname();
		}

	}

}
