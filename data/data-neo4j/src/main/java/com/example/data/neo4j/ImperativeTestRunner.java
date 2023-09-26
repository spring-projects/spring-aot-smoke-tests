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

import java.util.ArrayList;
import java.util.Map;

import org.neo4j.cypherdsl.core.Cypher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class ImperativeTestRunner implements CommandLineRunner {

	private final Neo4jTemplate neo4jTemplate;

	private final TransactionTemplate transactionTemplate;

	private final MovieRepository movieRepository;

	public ImperativeTestRunner(Neo4jTemplate neo4jTemplate, TransactionTemplate transactionTemplate,
			MovieRepository movieRepository) {
		this.neo4jTemplate = neo4jTemplate;
		this.transactionTemplate = transactionTemplate;
		this.movieRepository = movieRepository;
	}

	@Override
	public void run(String... args) {

		annotatedTypesShouldHaveBeenRegistered();
		externallyGeneratedFieldsShouldBePopulated();
		internallyGeneratedIdsShouldBePopulated();
		cypherDSLIntegrationShouldWork();
		qbeShouldWork();
	}

	private void qbeShouldWork() {

		log("---- Neo4j Query By Example (QBE) ----");
		var optionalMovie = movieRepository.findOne(Example.of(new Movie("A movie")));
		optionalMovie.ifPresent((movie) -> {
			log("Found one movie by example with id %s", movie.getId());
		});
	}

	private void cypherDSLIntegrationShouldWork() {

		log("---- Neo4j Cypher-DSL integration and relationship population ----");
		var title = Cypher.node("Movie").named("movie").property("title");
		var movies = new ArrayList<>(movieRepository.findAll(title.contains(Cypher.literalOf("A movie"))));

		log("Loaded %d movies", movies.size());
		if (!(movies.isEmpty() || movies.get(0).getActors().isEmpty())) {
			log("With %s actors, first named %s", movies.get(0).getActors().size(),
					movies.get(0).getActors().get(0).getName());
		}
	}

	private void internallyGeneratedIdsShouldBePopulated() {

		log("---- Neo4j internally generated values ----");
		var person = new Person();
		person.setName("Jane Doe");
		person = neo4jTemplate.save(person);
		log("Internal element id is present: %s", person.getId());
	}

	private void externallyGeneratedFieldsShouldBePopulated() {

		log("---- Neo4j externally generated values ----");
		var movie = transactionTemplate
			.execute(tx -> movieRepository.save(new Movie("One Flew Over the Cuckoos Nest")));

		log("Generated id is present: %s", movie.getId());
		log("CreatedAt is present: %s", DataNeo4jApplication.FIXED_DATE.equals(movie.getCreatedAt()));
		log("CreatedBy is present: %s", "Some person".equals(movie.getCreatedBy()));
		log("UpdatedAt is absent: %s", movie.getUpdatedAt() == null);
		log("UpdatedBy is absent: %s", movie.getUpdatedBy() == null);
		log("Version is %d", movie.getVersion());

		movie.setTitle("One Flew Over the Cuckoo's Nest");
		var updatedMovie = transactionTemplate.execute(tx -> movieRepository.save(movie));

		log("UpdatedAt is now present: %s", DataNeo4jApplication.FIXED_DATE.equals(updatedMovie.getUpdatedAt()));
		log("UpdatedBy is now present: %s", "Some person".equals(updatedMovie.getUpdatedBy()));
		log("Version is now %d", updatedMovie.getVersion());
	}

	private void annotatedTypesShouldHaveBeenRegistered() {

		log("---- Neo4j Managed types ----");

		var optionalResult = this.neo4jTemplate.findOne("MATCH (t:ParentLabel {name: $name}) RETURN t",
				Map.of("name", "BothLabelsMustBeManagedTypes"), ParentNode.class);
		optionalResult.ifPresent(node -> {

			log("All types are present: %s", node.getClass().getSimpleName());
			log("Id has been populated from DB: %s", node.getId());
		});
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
