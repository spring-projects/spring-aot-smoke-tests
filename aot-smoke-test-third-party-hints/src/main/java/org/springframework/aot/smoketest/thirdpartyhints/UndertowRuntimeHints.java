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

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

/**
 * A {@link RuntimeHintsRegistrar} for Undertow.
 *
 * @author Andy Wilkinson
 */
public class UndertowRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		invokePublicConstructorsOf(hints, "io.undertow.UndertowLogger_$logger",
				"io.undertow.server.protocol.http.HttpRequestParser$$generated",
				"io.undertow.servlet.UndertowServletLogger_$logger", "io.undertow.servlet.handlers.DefaultServlet",
				"io.undertow.util.FastConcurrentDirectDeque", "io.undertow.util.PortableConcurrentDirectDeque",
				"io.undertow.websockets.core.WebSocketLogger_$logger", "io.undertow.websockets.jsr.JsrWebSocketFilter",
				"io.undertow.websockets.jsr.JsrWebSocketFilter$LogoutListener",
				"io.undertow.websockets.jsr.JsrWebSocketLogger_$logger", "org.xnio._private.Messages_$logger",
				"org.xnio.nio.Log_$logger");
		registerMessages(hints, "io.undertow.UndertowMessages_$bundle",
				"io.undertow.servlet.UndertowServletMessages_$bundle",
				"io.undertow.websockets.core.WebSocketMessages_$bundle",
				"io.undertow.websockets.jsr.JsrWebSocketMessages_$bundle");
		hints.reflection().registerType(TypeReference.of("io.undertow.websockets.jsr.Bootstrap$WebSocketListener"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
	}

	private void invokePublicConstructorsOf(RuntimeHints hints, String... typeNames) {
		for (String typeName : typeNames) {
			hints.reflection().registerType(TypeReference.of(typeName), MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
		}
	}

	private void registerMessages(RuntimeHints hints, String... names) {
		for (String name : names) {
			hints.reflection().registerType(TypeReference.of(name), (hint) -> hint.withField("INSTANCE"));
		}
	}

}
