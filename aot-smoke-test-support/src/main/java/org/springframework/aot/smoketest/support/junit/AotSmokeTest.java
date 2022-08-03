/*
 * Copyright 2022 the original author or authors.
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
 * <li>{@link AssertableOutput} - application's redirected output</li>
 * <li>{@link WebTestClient} - preconfigured with a base URL for the application's
 * port</li>
 * <li>{@link DockerComposeHost} annotated <code>String</code> - host name of the
 * docker-compose service</li>
 * <li>{@link DockerComposePort} annotated <code>int</code> - port of the docker-compose
 * service</li>
 *
 * @author Andy Wilkinson
 * @author Moritz Halbritter
 */
@Retention(RUNTIME)
@Target(TYPE)
@Extensions({ @ExtendWith(AssertableOutputParameterResolver.class), @ExtendWith(WebTestClientParameterResolver.class),
		@ExtendWith(DockerComposeHostParameterResolver.class), @ExtendWith(DockerComposePortParameterResolver.class),
		@ExtendWith(AwaitApplication.class) })
public @interface AotSmokeTest {

}
