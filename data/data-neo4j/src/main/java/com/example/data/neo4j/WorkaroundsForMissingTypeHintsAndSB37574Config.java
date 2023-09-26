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

import java.util.List;
import java.util.Set;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.data.neo4j.aot.Neo4jManagedTypes;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.repository.query.CypherdslConditionExecutorImpl;
import org.springframework.data.neo4j.repository.query.ReactiveCypherdslConditionExecutorImpl;

/**
 * See https://github.com/spring-projects/spring-data-neo4j/issues/2798 Only needed until
 * https://github.com/spring-projects/spring-boot/pull/37574 is resolved Otherwise the
 * abscence of {@link ParentNode.ChildNode} will cause application startup failure
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(WorkaroundsForMissingTypeHintsAndSB37574Config.MissingRuntimeHints.class)
public class WorkaroundsForMissingTypeHintsAndSB37574Config {

	@Bean
	public Neo4jManagedTypes neo4jManagedTypes(ApplicationContext applicationContext) throws ClassNotFoundException {
		Set<Class<?>> initialEntityClasses = new EntityScanner(applicationContext).scan(Node.class,
				RelationshipProperties.class);
		return Neo4jManagedTypes.fromIterable(initialEntityClasses);
	}

	@Bean
	public Neo4jMappingContext neo4jMappingContext(Neo4jManagedTypes managedTypes, Neo4jConversions neo4jConversions) {

		Neo4jMappingContext context = new Neo4jMappingContext(neo4jConversions);
		context.setManagedTypes(managedTypes);
		return context;
	}

	public static class MissingRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.reflection()
				.registerTypes(
						List.of(TypeReference.of(CypherdslConditionExecutorImpl.class),
								TypeReference.of(ReactiveCypherdslConditionExecutorImpl.class)),
						builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
								MemberCategory.INVOKE_PUBLIC_METHODS));
		}

	}

}