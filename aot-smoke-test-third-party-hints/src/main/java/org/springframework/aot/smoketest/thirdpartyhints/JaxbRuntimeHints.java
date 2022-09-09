package org.springframework.aot.smoketest.thirdpartyhints;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

/**
 * @author Sebastien Deleuze
 */
public class JaxbRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection().registerType(TypeReference.of("org.glassfish.jaxb.runtime.v2.ContextFactory"),
				MemberCategory.INVOKE_DECLARED_METHODS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.model.impl.RuntimeModelBuilder"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(TypeReference.of("org.glassfish.jaxb.runtime.v2.model.impl.Utils"),
				MemberCategory.DECLARED_FIELDS);
		hints.reflection().registerType(TypeReference.of("org.glassfish.jaxb.core.v2.model.nav.ReflectionNavigator"),
				MemberCategory.INVOKE_DECLARED_METHODS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property.SingleElementLeafProperty"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property.ArrayElementLeafProperty"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property.SingleElementNodeProperty"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property.SingleReferenceNodeProperty"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property.SingleMapNodeProperty"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property.ArrayElementNodeProperty"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		hints.reflection().registerType(
				TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property.ArrayReferenceNodeProperty"),
				MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);

	}

}