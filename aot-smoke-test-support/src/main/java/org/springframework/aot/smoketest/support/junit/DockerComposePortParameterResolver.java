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
