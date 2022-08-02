package org.springframework.aot.smoketest.support.junit;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <code>String</code> parameters of JUnit test methods annotated with this annotation
 * will be resolved to the docker-compose host name for a given service.
 *
 * @author Moritz Halbritter
 */
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface DockerComposeHost {

	/**
	 * Name of the docker-compose service.
	 * @return name of the docker-compose service.
	 */
	String value();

}
