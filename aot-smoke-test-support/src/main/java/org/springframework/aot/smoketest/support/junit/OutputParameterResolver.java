package org.springframework.aot.smoketest.support.junit;

import java.io.File;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import org.springframework.aot.smoketest.support.assertj.Output;

/**
 * {@link ParameterResolver} for {@link Output}.
 *
 * @author Andy Wilkinson
 */
class OutputParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
		return Output.class.equals(parameterContext.getParameter().getType());
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		String property = System.getProperty("org.springframework.aot.smoketest.standard-output");
		if (property == null) {
			throw new IllegalStateException(
					"Standard output is not available as org.springframework.aot.smoketest.standard-output.txt system property has not been set");
		}
		return new Output(new File(property).toPath());
	}

}
