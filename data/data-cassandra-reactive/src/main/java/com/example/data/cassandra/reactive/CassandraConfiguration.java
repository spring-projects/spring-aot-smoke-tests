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

package com.example.data.cassandra.reactive;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.cql.generator.CreateKeyspaceCqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;

@Configuration
class CassandraConfiguration {

	@Bean
	CqlSession cqlSession(CqlSessionBuilder cqlSessionBuilder, CassandraProperties properties) {
		// This creates the keyspace on startup
		try (CqlSession session = cqlSessionBuilder.withKeyspace((String) null).build()) {
			session.execute(CreateKeyspaceCqlGenerator
				.toCql(CreateKeyspaceSpecification.createKeyspace(properties.getKeyspaceName()).ifNotExists()));
		}
		return cqlSessionBuilder.withKeyspace(properties.getKeyspaceName()).build();
	}

}
