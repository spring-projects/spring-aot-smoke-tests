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

package com.example.spring.orm;

import java.util.List;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

@SpringBootApplication
@EnableTransactionManagement
@ImportRuntimeHints(HibernateEnhancedApplication.Hibernate8RuntimeHints.class)
public class HibernateEnhancedApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(HibernateEnhancedApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class Hibernate8RuntimeHints implements RuntimeHintsRegistrar {

		private static final List<String> RELOCATED_MESSAGE_LOGGERS = List.of(
				"org.hibernate.sql.ast.internal.SqlAstTreeLogger", "org.hibernate.query.internal.QueryLogging",
				"org.hibernate.query.hql.internal.HqlLogging", "org.hibernate.boot.scan.internal.ScannerLogger",
				"org.hibernate.bytecode.enhance.internal.BytecodeEnhancementLogging",
				"org.hibernate.id.enhanced.SequenceGeneratorLogger",
				"org.hibernate.metamodel.mapping.internal.MappingModelCreationLogging",
				"org.hibernate.sql.results.internal.ResultsLogger");

		private static final String JBOSS_LOGGER = "org.jboss.logging.Logger";

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

			for (String messageLogger : RELOCATED_MESSAGE_LOGGERS) {

				String implementation = messageLogger + "_$logger";

				if (ClassUtils.isPresent(implementation, classLoader)) {
					hints.reflection()
						.registerType(TypeReference.of(implementation), (hint) -> hint
							.withConstructor(List.of(TypeReference.of(JBOSS_LOGGER)), ExecutableMode.INVOKE));
				}
			}
		}

	}

}
