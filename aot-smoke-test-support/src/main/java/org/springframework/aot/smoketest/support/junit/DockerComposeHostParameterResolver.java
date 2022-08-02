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
