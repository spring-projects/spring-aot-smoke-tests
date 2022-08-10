package com.example.servlet.tomcat;

import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.junit.AotSmokeTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
class ServletTomcatApplicationAotTests {

	@Test
	void servletIsInvokable(WebTestClient client) {
		client.get().uri("/?name=Servlet").exchange().expectStatus().isOk().expectBody().consumeWith(
				(result) -> assertThat(new String(result.getResponseBodyContent())).isEqualTo("Hello Servlet"));
	}

}
