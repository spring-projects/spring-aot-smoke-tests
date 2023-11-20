/*
 * Copyright 2022-2023 the original author or authors.
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

package org.springframework.aot.gradle;

import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link UseSnapshots}.
 *
 * @author Andy Wilkinson
 */
class UseSnapshotsTests {

	private final UseSnapshots useSnapshots = new UseSnapshots();

	@ParameterizedTest
	@CsvSource(textBlock = """
				6.0.12-SNAPSHOT, 6.0.12-SNAPSHOT
				5.2.26.BUILD-SNAPSHOT, 5.2.26.BUILD-SNAPSHOT
			""")
	void snapshotVersionIsUsedAsIs(String version, String derivedVersion) {
		DependencyResolveDetails dependency = dependency("org.springframework", version);
		then(dependency).should().useVersion(derivedVersion);
	}

	@ParameterizedTest
	@CsvSource(textBlock = """
				6.1.0-M2, 6.1.0-SNAPSHOT
				5.2.0.M2, 5.2.0.BUILD-SNAPSHOT
			""")
	void milestoneUsesSnapshotOfSameVersion(String version, String derivedVersion) {
		DependencyResolveDetails dependency = dependency("org.springframework", version);
		then(dependency).should().useVersion(derivedVersion);
	}

	@ParameterizedTest
	@CsvSource(textBlock = """
				6.1.0-RC1, 6.1.0-SNAPSHOT
				5.2.0.RC1, 5.2.0.BUILD-SNAPSHOT
			""")
	void releaseCandidateUsesSnapshotOfSameVersion(String version, String derivedVersion) {
		DependencyResolveDetails dependency = dependency("org.springframework", version);
		then(dependency).should().useVersion(derivedVersion);
	}

	@ParameterizedTest
	@CsvSource(textBlock = """
				6.1.0, 6.1.1-SNAPSHOT
				5.2.0.RELEASE, 5.2.1.BUILD-SNAPSHOT
			""")
	void gaReleaseUsesSnapshotOfNextPatch(String version, String derivedVersion) {
		DependencyResolveDetails dependency = dependency("org.springframework", version);
		then(dependency).should().useVersion(derivedVersion);
	}

	@ParameterizedTest
	@CsvSource(textBlock = """
				6.1.1, 6.1.2-SNAPSHOT
				5.2.1.RELEASE, 5.2.2.BUILD-SNAPSHOT
			""")
	void maintenanceReleaseUsesSnapshotOfNextPatch(String version, String derivedVersion) {
		DependencyResolveDetails dependency = dependency("org.springframework", version);
		then(dependency).should().useVersion(derivedVersion);
	}

	@Test
	void springBootDependenciesAreNotAffected() {
		DependencyResolveDetails dependency = dependency("org.springframework.boot", "3.1.5");
		then(dependency).shouldHaveNoMoreInteractions();
	}

	@Test
	void springCloudDependenciesAreNotAffected() {
		DependencyResolveDetails dependency = dependency("org.springframework.cloud", "3.1.5");
		then(dependency).shouldHaveNoMoreInteractions();
	}

	@Test
	void thirdPartyDependenciesAreNotAffected() {
		DependencyResolveDetails dependency = dependency("org.apache.tomcat", "10.1.5");
		then(dependency).shouldHaveNoMoreInteractions();
	}

	@Test
	void versionlessDependenciesAreNotAffected() {
		DependencyResolveDetails dependency = dependency("org.springframework", "");
		then(dependency).shouldHaveNoMoreInteractions();
	}

	private DependencyResolveDetails dependency(String group, String version) {
		DependencyResolveDetails dependency = mock(DependencyResolveDetails.class);
		ModuleVersionSelector selector = mock(ModuleVersionSelector.class);
		given(dependency.getRequested()).willReturn(selector);
		given(selector.getGroup()).willReturn(group);
		given(selector.getVersion()).willReturn(version);
		this.useSnapshots.execute(dependency);
		then(dependency).should().getRequested();
		return dependency;
	}

}
