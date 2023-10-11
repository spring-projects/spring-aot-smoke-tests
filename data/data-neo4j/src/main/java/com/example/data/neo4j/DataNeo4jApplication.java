/*
 * Copyright 2023 the original author or authors.
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
package com.example.data.neo4j;

import java.time.LocalDateTime;
import java.util.Optional;

import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.neo4j.driver.Driver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.config.EnableReactiveNeo4jAuditing;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableNeo4jAuditing(auditorAwareRef = "fixedAuditor", dateTimeProviderRef = "fixedTime")
@EnableReactiveNeo4jAuditing(auditorAwareRef = "reactiveFixedAuditor", dateTimeProviderRef = "fixedTime")
public class DataNeo4jApplication {

	public static final LocalDateTime FIXED_DATE = LocalDateTime.of(2023, 9, 21, 7, 0, 0, 0);

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DataNeo4jApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@Bean
	AuditorAware<String> fixedAuditor() {
		return () -> Optional.of("Some person");
	}

	@Bean
	ReactiveAuditorAware<String> reactiveFixedAuditor() {
		return () -> Mono.just("Some person");
	}

	@Bean
	DateTimeProvider fixedTime() {
		return () -> Optional.of(FIXED_DATE);
	}

	@Bean
	Configuration cypherDslConfiguration() {
		return Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(Driver driver,
			DatabaseSelectionProvider databaseNameProvider) {
		return new Neo4jTransactionManager(driver, databaseNameProvider);
	}

	@Bean
	public ReactiveNeo4jTransactionManager reactiveTransactionManager(Driver driver,
			ReactiveDatabaseSelectionProvider databaseNameProvider) {
		return new ReactiveNeo4jTransactionManager(driver, databaseNameProvider);
	}

}
