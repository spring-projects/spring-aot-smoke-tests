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

import java.util.Locale;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * {@link ParameterResolver} for {@link DockerComposeHost} annotated <code>String</code>s.
 *
 * @author Moritz Halbritter
 */
class DockerComposeHostParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return String.class.equals(parameterContext.getParameter().getType())
				&& parameterContext.isAnnotated(DockerComposeHost.class);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		DockerComposeHost annotation = parameterContext.findAnnotation(DockerComposeHost.class).get();
		String serviceName = annotation.value();
		String envVariable = serviceName.toUpperCase(Locale.ENGLISH) + "_HOST";
		String value = System.getenv(envVariable);
		if (value == null) {
			throw new ParameterResolutionException(
					"Can't get host name for docker-compose service '%s', the environment variable '%s' is missing"
							.formatted(serviceName, envVariable));
		}
		return value;
	}

}
