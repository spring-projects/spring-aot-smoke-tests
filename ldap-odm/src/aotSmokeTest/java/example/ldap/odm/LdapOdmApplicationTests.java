package example.ldap.odm;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AotSmokeTest
public class LdapOdmApplicationTests {

	@Test
	void shouldFindAllPeople(WebTestClient client) {
		client.get().uri("/person").exchange().expectBody().json("""
				  [
					  {
						  "company": "company1",
						  "country": "Sweden",
						  "description": "Sweden, Company1, Some Person",
						  "fullName": "Some Person",
						  "lastName": "Person",
						  "phone": "+46 555-123456"
					  },
					  {
						  "company": "company1",
						  "country": "Sweden",
						  "description": "Sweden, Company1, Some Person2",
						  "fullName": "Some Person2",
						  "lastName": "Person2",
						  "phone": "+46 555-654321"
					  }
				  ]
				""");
	}

	@Test
	void shouldFindPerson(WebTestClient client) {
		// @formatter:off
		client.get().uri((builder) -> builder.path("/person")
						.queryParam("country", "Sweden")
						.queryParam("company", "company1")
						.queryParam("fullName", "Some Person2").build()
				).exchange().expectBody().json("""
						{
						  "company": "company1",
						  "country": "Sweden",
						  "description": "Sweden, Company1, Some Person2",
						  "fullName": "Some Person2",
						  "lastName": "Person2",
						  "phone": "+46 555-654321"
						}
				""");
		// @formatter:on
	}

	@Test
	void shouldAddNewPerson(WebTestClient client) {
		client.post().uri("/person").exchange().expectStatus().isOk();
	}

	@Test
	void shouldDeletePerson(WebTestClient client) {
		// @formatter:off
		client.delete().uri((builder) -> builder.path("/person")
				.queryParam("country", "Sweden")
				.queryParam("company", "company1")
				.queryParam("fullName", "Some Person2").build()
		).exchange().expectStatus().isOk();
		// @formatter:on
	}

}
