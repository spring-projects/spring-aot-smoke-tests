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

import java.net.URI;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * {@link ParameterResolver} for {@link ApplicationUrl} annotated {@link URI}s.
 *
 * @author Moritz Halbritter
 */
public class ApplicationUrlParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return URI.class.equals(parameterContext.getParameter().getType())
				&& parameterContext.isAnnotated(ApplicationUrl.class);

	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		ApplicationUrl annotation = parameterContext.findAnnotation(ApplicationUrl.class).get();
		return URI.create(annotation.scheme().getScheme() + "://localhost:"
				+ ApplicationUnderTest.get(extensionContext).getPort());
	}

}
