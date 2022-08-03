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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <code>String</code> parameters of JUnit test methods annotated with this annotation
 * will be resolved to the docker-compose host name for a given service.
 *
 * @author Moritz Halbritter
 */
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface DockerComposeHost {

	/**
	 * Name of the docker-compose service.
	 * @return name of the docker-compose service.
	 */
	String value();

}
