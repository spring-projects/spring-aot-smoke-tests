package org.springframework.aot.smoketest.support.junit;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;

/**
 * {@link ParameterResolver} for {@link AssertableOutput}.
 *
 * @author Andy Wilkinson
 */
class AssertableOutputParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
		return AssertableOutput.class.equals(parameterContext.getParameter().getType());
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return new AssertableOutput();
	}

}
