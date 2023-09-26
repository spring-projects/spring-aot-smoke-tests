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

import java.util.Map;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.driver.Driver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Component
public class ReactiveTestRunner implements CommandLineRunner {

	private final Driver driver;

	private final ReactiveNeo4jTemplate neo4jTemplate;

	private final TransactionalOperator transactionalOperator;

	private final ReactiveMovieRepository movieRepository;

	public ReactiveTestRunner(Driver driver, ReactiveNeo4jTemplate neo4jTemplate,
			ReactiveTransactionManager reactiveTransactionManager, ReactiveMovieRepository movieRepository) {
		this.driver = driver;
		this.neo4jTemplate = neo4jTemplate;
		this.transactionalOperator = TransactionalOperator.create(reactiveTransactionManager);
		this.movieRepository = movieRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		annotatedTypesShouldHaveBeenRegistered();
		externallyGeneratedFieldsShouldBePopulated();
		internallyGeneratedIdsShouldBePopulated();
		cypherDSLIntegrationShouldWork();
		qbeShouldWork();
	}

	private void qbeShouldWork() {

		log("---- [Reactive] Neo4j Query By Example (QBE) ----");
		var optionalMovie = movieRepository.findOne(Example.of(new Movie("A movie"))).blockOptional();
		optionalMovie.ifPresent((movie) -> {
			log("[Reactive] Found one movie by example with id %s", movie.getId());
		});
	}

	private void cypherDSLIntegrationShouldWork() {

		log("---- [Reactive] Neo4j Cypher-DSL integration and relationship population ----");
		var title = Cypher.node("Movie").named("movie").property("title");
		var movies = movieRepository.findAll(title.contains(Cypher.literalOf("A movie"))).collectList().block();

		log("[Reactive] Loaded %d movies", movies.size());
		if (!(movies.isEmpty() || movies.get(0).getActors().isEmpty())) {
			log("[Reactive] With %s actors, first named %s", movies.get(0).getActors().size(),
					movies.get(0).getActors().get(0).getName());
		}
	}

	private void internallyGeneratedIdsShouldBePopulated() {

		log("---- [Reactive] Neo4j internally generated values ----");
		var person = new Person();
		person.setName("Jane Doe");
		person = neo4jTemplate.save(person).block();
		log("[Reactive] Internal element id is present: %s", person.getId());
	}

	private void externallyGeneratedFieldsShouldBePopulated() {

		log("---- [Reactive] Neo4j externally generated values ----");
		var movie = movieRepository.save(new Movie("One Flew Over the Cuckoos Nest"))
			.as(transactionalOperator::transactional)
			.block();

		log("[Reactive] Generated id is present: %s", movie.getId());
		log("[Reactive] CreatedAt is present: %s", DataNeo4jApplication.FIXED_DATE.equals(movie.getCreatedAt()));
		log("[Reactive] CreatedBy is present: %s", "Some person".equals(movie.getCreatedBy()));
		log("[Reactive] UpdatedAt is absent: %s", movie.getUpdatedAt() == null);
		log("[Reactive] UpdatedBy is absent: %s", movie.getUpdatedBy() == null);
		log("[Reactive] Version is %d", movie.getVersion());

		movie.setTitle("One Flew Over the Cuckoo's Nest");
		var updatedMovie = movieRepository.save(movie).as(transactionalOperator::transactional).block();

		log("[Reactive] UpdatedAt is now present: %s",
				DataNeo4jApplication.FIXED_DATE.equals(updatedMovie.getUpdatedAt()));
		log("[Reactive] UpdatedBy is now present: %s", "Some person".equals(updatedMovie.getUpdatedBy()));
		log("[Reactive] Version is now %d", updatedMovie.getVersion());
	}

	private void annotatedTypesShouldHaveBeenRegistered() {

		log("---- [Reactive] Neo4j Managed types ----");

		var optionalResult = this.neo4jTemplate
			.findOne("MATCH (t:ParentLabel {name: $name}) RETURN t", Map.of("name", "BothLabelsMustBeManagedTypes"),
					ParentNode.class)
			.blockOptional();
		optionalResult.ifPresent(node -> {

			log("[Reactive] All types are present: %s", node.getClass().getSimpleName());
			log("[Reactive] Id has been populated from DB: %s", node.getId());
		});
	}

	private void initializeDatabase(String... args) {
		try (var session = driver.session(); var tx = session.beginTransaction()) {
			tx.run("MATCH (n:ParentLabel:ChildLabel) DETACH DELETE n");
			tx.run("CREATE (:ParentLabel:ChildLabel {id: randomUuid(), name: 'BothLabelsMustBeManagedTypes'})");
			tx.run("CREATE (:Movie {id: randomUuid(), version: 0, title: 'A movie', updatedBy: 'A person', updatedAt: localdatetime()}) <-[:ACTED_IN]-(:Person {name: 'An Actor'})");
			tx.commit();
		}
	}

	private void log(Object value) {
		log(String.valueOf(value), new Object[0]);
	}

	private void log(String message, Object... arguments) {
		String messageNewline = String.valueOf(message).endsWith("\n") ? message : message + "\n";
		System.out.printf(messageNewline, arguments);
		System.out.flush();
	}

}
