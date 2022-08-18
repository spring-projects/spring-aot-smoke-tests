package example.ldap.odm;

import example.ldap.odm.LdapOdmApplication.LdapOdmApplicationRuntimeHints;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(LdapOdmApplicationRuntimeHints.class)
public class LdapOdmApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LdapOdmApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class LdapOdmApplicationRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			// TODO: should be contributed to third party metadata
			hints.resources().registerPattern("com/unboundid/ldap/sdk/schema/standard-schema.ldif");
		}

	}

}
