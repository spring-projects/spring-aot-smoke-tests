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
 * {@link ParameterResolver} for {@link DockerComposePort} annotated <code>int</code>s.
 *
 * @author Moritz Halbritter
 */
class DockerComposePortParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return int.class.equals(parameterContext.getParameter().getType())
				&& parameterContext.isAnnotated(DockerComposePort.class);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		DockerComposePort annotation = parameterContext.findAnnotation(DockerComposePort.class).get();
		String serviceName = annotation.service();
		int port = annotation.port();
		String envVariable = serviceName.toUpperCase(Locale.ENGLISH) + "_PORT_" + port;
		String value = System.getenv(envVariable);
		if (value == null) {
			throw new ParameterResolutionException(
					"Can't get mapped port for docker-compose service '%s' port %d, the environment variable '%s' is missing"
							.formatted(serviceName, port, envVariable));
		}
		return Integer.parseInt(value);
	}

}
