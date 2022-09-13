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

package org.springframework.aot.smoketest.thirdpartyhints;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeHint.Builder;
import org.springframework.aot.hint.TypeReference;

/**
 * A {@link RuntimeHintsRegistrar} for Jetty.
 *
 * @author Andy Wilkinson
 */
public class JettyRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		invokePublicConstructorsOf(hints, "org.eclipse.jetty.webapp.ClassMatcher$ByPackageOrName",
				"org.eclipse.jetty.webapp.ClassMatcher$ByLocationOrModule",
				"org.eclipse.jetty.security.ConstraintSecurityHandler");
		invokeValueOfOn(hints, Boolean.class, Byte.class, Double.class, Float.class, Integer.class, Long.class,
				Short.class);
	}

	private void invokePublicConstructorsOf(RuntimeHints hints, String... typeNames) {
		for (String typeName : typeNames) {
			hints.reflection().registerType(TypeReference.of(typeName), MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
		}
	}

	private void invokeValueOfOn(RuntimeHints hints, Class<?>... types) {
		for (Class<?> type : types) {
			hints.reflection().registerType(type, (Consumer<Builder>) (hint) -> hint.withMethod("valueOf",
					List.of(TypeReference.of(String.class)), ExecutableMode.INVOKE));
		}
	}

}
