/*
 * Copyright 2023 the original author or authors.
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
package neo4j;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

@ApplicationTest
class DataNeo4jApplicationAotTests {

	@Test
	void annotatedTypesShouldHaveBeenRegistered(AssertableOutput output) {
		List<String> expectedLines = List.of("All types are present: ChildNode", "Id has been populated from DB: \\d+");

		assertExpectedLines(output, expectedLines);
	}

	@Test
	void externallyGeneratedFieldsShouldBePopulated(AssertableOutput output) {
		List<String> expectedLines = List.of("Generated id is present: \\d+", "CreatedAt is present: true",
				"CreatedBy is present: true", "UpdatedAt is absent: false", "UpdatedBy is absent: false",
				"Version is 0", "UpdatedAt is now present: true", "UpdatedBy is now present: true", "Version is now 1");

		assertExpectedLines(output, expectedLines);
	}

	@Test
	void internallyGeneratedIdsShouldBePopulated(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasLineMatching("Internal element id is present: \\d+")
				.hasLineMatching("\\[Reactive\\] Internal element id is present: \\d+");
		});
	}

	@Test
	void cypherDSLIntegrationShouldWork(AssertableOutput output) {
		List<String> expectedLines = List.of("Loaded 1 movies", "With 1 actors, first named An Actor");

		assertExpectedLines(output, expectedLines);
	}

	private static void assertExpectedLines(AssertableOutput output, List<String> expectedLines) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			expectedLines.forEach(line -> assertThat(output).hasLineMatching(line));
			expectedLines.forEach(line -> assertThat(output).hasLineMatching("\\[Reactive\\] " + line));
		});
	}

	@Test
	void qbeShouldWork(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(10))
			.untilAsserted(() -> assertThat(output).hasLineMatching("Found one movie by example with id \\d+")
				.hasLineMatching("\\[Reactive\\] Found one movie by example with id \\d+"));
	}

}
