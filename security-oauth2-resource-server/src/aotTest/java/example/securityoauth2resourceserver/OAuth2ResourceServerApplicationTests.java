package example.securityoauth2resourceserver;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
public class OAuth2ResourceServerApplicationTests {

	private static final String NONE_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQ1ODgwLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMDFkOThlZWEtNjc0MC00OGRlLTk4ODAtYzM5ZjgyMGZiNzVlIiwiY2xpZW50X2lkIjoibm9zY29wZXMiLCJzY29wZSI6WyJub25lIl19.VOzgGLOUuQ_R2Ur1Ke41VaobddhKgUZgto7Y3AGxst7SuxLQ4LgWwdSSDRx-jRvypjsCgYPbjAYLhn9nCbfwtCitkymUKUNKdebvVAI0y8YvliWTL5S-GiJD9dN8SSsXUla9A4xB_9Mt5JAlRpQotQSCLojVSKQmjhMpQWmYAlKVjnlImoRwQFPI4w3Ijn4G4EMTKWUYRfrD0-WNT9ZYWBeza6QgV6sraP7ToRB3eQLy2p04cU40X-RHLeYCsMBfxsMMh89CJff-9tn7VDKi1hAGc_Lp9yS9ZaItJuFJTjf8S_vsjVB1nBhvdS_6IED_m_fOU52KiGSO2qL6shxHvg";

	private static final String READ_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQ1NjQ4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiY2I1ZGMwNDYtMDkyMi00ZGJmLWE5MzAtOGI2M2FhZTYzZjk2IiwiY2xpZW50X2lkIjoicmVhZGVyIiwic2NvcGUiOlsibWVzc2FnZTpyZWFkIl19.Pre2ksnMiOGYWQtuIgHB0i3uTnNzD0SMFM34iyQJHK5RLlSjge08s9qHdx6uv5cZ4gZm_cB1D6f4-fLx76bCblK6mVcabbR74w_eCdSBXNXuqG-HNrOYYmmx5iJtdwx5fXPmF8TyVzsq_LvRm_LN4lWNYquT4y36Tox6ZD3feYxXvHQ3XyZn9mVKnlzv-GCwkBohCR3yPow5uVmr04qh_al52VIwKMrvJBr44igr4fTZmzwRAZmQw5rZeyep0b4nsCjadNcndHtMtYKNVuG5zbDLsB7GGvilcI9TDDnUXtwthB_3iq32DAd9x8wJmJ5K8gmX6GjZFtYzKk_zEboXoQ";

	private static final String WRITE_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQzOTA0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZGI4ZjgwMzQtM2VlNy00NjBjLTk3NTEtMDJiMDA1OWI5NzA4IiwiY2xpZW50X2lkIjoid3JpdGVyIiwic2NvcGUiOlsibWVzc2FnZTp3cml0ZSJdfQ.USvpx_ntKXtchLmc93auJq0qSav6vLm4B7ItPzhrDH2xmogBP35eKeklwXK5GCb7ck1aKJV5SpguBlTCz0bZC1zAWKB6gyFIqedALPAran5QR-8WpGfl0wFqds7d8Jw3xmpUUBduRLab9hkeAhgoVgxevc8d6ITM7kRnHo5wT3VzvBU8DquedVXm5fbBnRPgG4_jOWJKbqYpqaR2z2TnZRWh3CqL82Orh1Ww1dJYF_fae1dTVV4tvN5iSndYcGxMoBaiw3kRRi6EyNxnXnt1pFtZqc1f6D9x4AHiri8_vpBp2vwG5OfQD5-rrleP_XlIB3rNQT7tu3fiqu4vUzQaEg";

	@Test
	void shouldRespondWhenTokenPresent(WebTestClient client) {
		client.get().uri("/").header("Authorization", bearer(NONE_JWT)).exchange().expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.isEqualTo("Hello, subject!"));
	}

	@Test
	void shouldAllowGetWhenTokenWithReadScope(WebTestClient client) {
		client.get().uri("/message").header("Authorization", bearer(READ_JWT)).exchange().expectStatus().isOk()
				.expectBody().consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.isEqualTo("secret message"));
	}

	@Test
	void shouldBlockGetWhenTokenWithNoScope(WebTestClient client) {
		client.get().uri("/message").header("Authorization", bearer(NONE_JWT)).exchange().expectStatus().isForbidden();
	}

	@Test
	void shouldAllowPostWhenTokenWithScope(WebTestClient client) {
		client.post().uri("/message").header("Authorization", bearer(WRITE_JWT)).bodyValue("my message").exchange()
				.expectStatus().isOk().expectBody()
				.consumeWith((result) -> assertThat(new String(result.getResponseBodyContent()))
						.isEqualTo("Message was created. Content: my message"));
	}

	@Test
	void shouldBlockPostWhenTokenWithBoScope(WebTestClient client) {
		client.post().uri("/message").header("Authorization", bearer(NONE_JWT)).bodyValue("my message").exchange()
				.expectStatus().isForbidden();
	}

	private static String bearer(String token) {
		return "Bearer " + token;
	}

}
