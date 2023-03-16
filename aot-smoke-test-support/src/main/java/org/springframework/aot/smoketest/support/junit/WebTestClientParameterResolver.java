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

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.Builder;

/**
 * A {@link ParameterResolver} for {@link WebTestClient}. The {@code WebTestClient} is
 * built with a {@link Builder#baseUrl(String) base URL} for the application under test.
 *
 * @author Andy Wilkinson
 */
class WebTestClientParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return WebTestClient.class.equals(parameterContext.getParameter().getType());
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Store store = extensionContext.getRoot().getStore(Namespace.create(WebTestClient.class));
		return store.getOrComputeIfAbsent(WebTestClient.class, (c) -> createWebTestClient(extensionContext));
	}

	private WebTestClient createWebTestClient(ExtensionContext extensionContext) {
		return WebTestClient.bindToServer()
			.baseUrl("http://localhost:" + ApplicationUnderTest.get(extensionContext).getPort())
			.build();
	}

}
