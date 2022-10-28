package com.example.security.oauth2authorizationserver;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.ApplicationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@ApplicationTest
class OAuth2AuthorizationServerApplicationAotTests {

	private static final String CLIENT_ID = "messaging-client";

	private static final String CLIENT_SECRET = "secret";

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void performTokenRequestWhenValidClientCredentialsThenOk(WebTestClient client) {
		// @formatter:off
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "client_credentials");
		formData.add("scope", "message:read");
		client.post().uri("/oauth2/token").headers((headers) -> headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET))
				.body(BodyInserters.fromFormData(formData))
				.exchange().expectStatus().isOk()
				.expectBody().jsonPath("$.access_token").exists()
				.jsonPath("$.expires_in").isNumber()
				.jsonPath("$.scope").isEqualTo("message:read")
				.jsonPath("$.token_type").isEqualTo("Bearer");
		// @formatter:on
	}

	@Test
	void performTokenRequestWhenMissingScopeThenOk(WebTestClient client) {
		// @formatter:off
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "client_credentials");
		formData.add("scope", "message:read message:write");
		client.post().uri("/oauth2/token").headers((headers) -> headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET))
				.body(BodyInserters.fromFormData(formData))
				.exchange().expectStatus().isOk()
				.expectBody().jsonPath("$.access_token").exists()
				.jsonPath("$.expires_in").isNumber()
				.jsonPath("$.scope").isEqualTo("message:read message:write")
				.jsonPath("$.token_type").isEqualTo("Bearer");
		// @formatter:on
	}

	@Test
	void performTokenRequestWhenInvalidClientCredentialsThenUnauthorized(WebTestClient client) {
		// @formatter:off
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "client_credentials");
		formData.add("scope", "message:read");
		client.post().uri("/oauth2/token").headers(badCredentials())
				.body(BodyInserters.fromFormData(formData))
				.exchange().expectStatus().isUnauthorized()
				.expectBody().jsonPath("$.error").isEqualTo("invalid_client");
		// @formatter:on
	}

	@Test
	void performTokenRequestWhenMissingGrantTypeThenUnauthorized(WebTestClient client) {
		// @formatter:off
		client.post().uri("/oauth2/token").headers(badCredentials())
				.exchange().expectStatus().isUnauthorized()
				.expectBody().jsonPath("$.error").isEqualTo("invalid_client");
		// @formatter:on
	}

	@Test
	void performTokenRequestWhenGrantTypeNotRegisteredThenBadRequest(WebTestClient client) {
		// @formatter:off
		client.post().uri("/oauth2/token").headers((headers) -> headers.setBasicAuth("login-client", "openid-connect"))
				.body(BodyInserters.fromFormData("grant_type", "client_credentials"))
				.exchange().expectStatus().isBadRequest()
				.expectBody().jsonPath("$.error").isEqualTo("unauthorized_client");
		// @formatter:on
	}

	@Test
	void performIntrospectionRequestWhenValidTokenThenOk(WebTestClient client) throws Exception {
		// @formatter:off
		client.post().uri("/oauth2/introspect").headers((headers) -> headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET))
				.body(BodyInserters.fromFormData("token", getAccessToken(client)))
				.exchange().expectStatus().isOk()
				.expectBody().jsonPath("$.active").isEqualTo("true")
				.jsonPath("$.aud[0]").isEqualTo(CLIENT_ID)
				.jsonPath("$.client_id").isEqualTo(CLIENT_ID)
				.jsonPath("$.exp").isNumber()
				.jsonPath("$.iat").isNumber()
				.jsonPath("$.iss").isEqualTo("http://localhost:9000")
				.jsonPath("$.nbf").isNumber()
				.jsonPath("$.scope").isEqualTo("message:read")
				.jsonPath("$.sub").isEqualTo(CLIENT_ID)
				.jsonPath("$.token_type").isEqualTo("Bearer");
		// @formatter:on
	}

	@Test
	void performIntrospectionRequestWhenInvalidCredentialsThenUnauthorized(WebTestClient client) throws Exception {
		// @formatter:off
		client.post().uri("/oauth2/introspect").headers(badCredentials())
				.body(BodyInserters.fromFormData("token", getAccessToken(client)))
				.exchange().expectStatus().isUnauthorized()
				.expectBody().jsonPath("$.error").isEqualTo("invalid_client");
		// @formatter:on
	}

	private static Consumer<HttpHeaders> badCredentials() {
		return (headers) -> headers.setBasicAuth("bad", "password");
	}

	private String getAccessToken(WebTestClient client) throws IOException {
		// @formatter:off
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "client_credentials");
		formData.add("scope", "message:read");
		byte[] responseBody = client.post().uri("/oauth2/token")
				.headers((headers) -> headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET))
				.body(BodyInserters.fromFormData(formData))
				.exchange().expectStatus().isOk()
				.expectBody().jsonPath("$.access_token").exists()
				.returnResult().getResponseBody();
		// @formatter:on

		Map<String, Object> tokenResponse = this.objectMapper.readValue(responseBody, new TypeReference<>() {
		});

		return tokenResponse.get("access_token").toString();
	}

}
