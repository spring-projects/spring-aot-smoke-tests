package com.example.data.rest.mongodb;

import java.net.URI;
import java.util.regex.Pattern;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class DataRestMongoDbApplicationAotTests {

	@Test
	void indexHasLinks(WebTestClient client) {
		client.get().uri("/").exchange().expectBody().jsonPath("$._links.persons.href").value((href) -> {
			assertThat(href).asInstanceOf(InstanceOfAssertFactories.STRING)
					.matches(Pattern.compile("http://localhost:\\d+/person\\{\\?page,size,sort}"));
		}).jsonPath("$._links.profile.href").value((href) -> {
			assertThat(href).asInstanceOf(InstanceOfAssertFactories.STRING)
					.matches(Pattern.compile("http://localhost:\\d+/profile"));
		});
	}

	@Test
	void shouldReturnAllPersons(WebTestClient client) {
		client.get().uri("/person?sort=firstname,asc").exchange().expectBody()
				.jsonPath("$._embedded.persons[0].firstname").isEqualTo("first-1")
				.jsonPath("$._embedded.persons[0].lastname").isEqualTo("last-1")
				.jsonPath("$._embedded.persons[1].firstname").isEqualTo("first-2")
				.jsonPath("$._embedded.persons[1].lastname").isEqualTo("last-2")
				.jsonPath("$._embedded.persons[2].firstname").isEqualTo("first-3")
				.jsonPath("$._embedded.persons[2].lastname").isEqualTo("last-3");
	}

	@Test
	void shouldCreateNewPerson(WebTestClient client) {
		FluxExchangeResult<String> result = client.post().uri("/person").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("""
						{
						    "firstname": "test-first-1",
						    "lastname": "test-last-1"
						}
						""").exchange().expectStatus().isCreated().returnResult(String.class);

		result.getResponseBody().ignoreElements().block();
		URI location = result.getResponseHeaders().getLocation();
		assertThat(location).isNotNull();

		client.get().uri(location).exchange().expectBody().jsonPath("$.firstname").isEqualTo("test-first-1")
				.jsonPath("$.lastname").isEqualTo("test-last-1");
	}

}
