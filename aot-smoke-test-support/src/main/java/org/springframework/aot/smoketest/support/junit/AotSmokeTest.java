package org.springframework.aot.smoketest.support.junit;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * JUnit Jupiter {@link ExtendWith extension} for an AOT smoke test. Allows the following
 * types to be injected as parameters:
 *
 * <ul>
 * <li>{@link AssertableOutput} – application's redirected output</li>
 * <li>{@link WebTestClient} – preconfigured with a base URL for the application's
 * port</li>
 *
 * @author Andy Wilkinson
 */
@Retention(RUNTIME)
@Target(TYPE)
@Extensions({ @ExtendWith(AssertableOutputParameterResolver.class), @ExtendWith(WebTestClientParameterResolver.class),
		@ExtendWith(AwaitApplication.class) })
public @interface AotSmokeTest {

}
