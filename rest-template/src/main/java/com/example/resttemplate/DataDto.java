package com.example.resttemplate;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.aot.BindingReflectionHintsRegistrar;

public class DataDto {

	private String url;

	private String method;

	public DataDto() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "DataDto{" + "url='" + url + '\'' + ", method='" + method + '\'' + '}';
	}

	static class DataDtoRuntimeHints implements RuntimeHintsRegistrar {

		private final BindingReflectionHintsRegistrar bindingReflectionHintsRegistrar = new BindingReflectionHintsRegistrar();

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			this.bindingReflectionHintsRegistrar.registerReflectionHints(hints.reflection(), DataDto.class);
		}

	}

}
