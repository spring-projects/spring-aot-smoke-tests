package example.ldap.odm;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(LdapOdmApplication.Hints.class)
public class LdapOdmApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LdapOdmApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	static class Hints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			// TODO Should this be in Spring Boot?
			// https://github.com/spring-projects/spring-boot/issues/32084
			hints.resources().registerPattern(".*.ldif");
		}

	}

}
