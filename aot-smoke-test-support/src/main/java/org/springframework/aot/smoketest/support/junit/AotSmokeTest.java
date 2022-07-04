package org.springframework.aot.smoketest.support.junit;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * JUnit Jupiter {@link ExtendWith extension} for an AOT smoke test.
 *
 * @author Andy Wilkinson
 */
@Retention(RUNTIME)
@Target(TYPE)
@ExtendWith(OutputParameterResolver.class)
public @interface AotSmokeTest {

}
