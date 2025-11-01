/*
 * Copyright 2022-2024 the original author or authors.
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

import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ResolutionStrategy;

/**
 * {@link ResolutionStrategy#eachDependency(Action) Resolution strategy action} that
 * forces dependencies to use snapshots. The version that is used is derived from a
 * dependency's current version:
 * <ul>
 * <li>{@code x.y.z-SNAPSHOT} is used as-is</li>
 * <li>{@code x.y.z.BUILD-SNAPSHOT} is used as-is</li>
 * <li>{@code x.y.z-Mn} is changed to {@code to x.y.z-SNAPSHOT}</li>
 * <li>{@code x.y.z.Mn} is changed to {@code to x.y.z.BUILD-SNAPSHOT}</li>
 * <li>{@code x.y.z-RCn} is changed to {@code to x.y.z-SNAPSHOT}</li>
 * <li>{@code x.y.z.RCn} is changed to {@code to x.y.z.BUILD-SNAPSHOT}</li>
 * <li>{@code x.y.z} is changed to {@code x.y.z+1-SNAPSHOT}</li>
 * <li>{@code x.y.z.RELEASE} is changed to {@code x.y.z+1.BUILD-SNAPSHOT}</li>
 *
 * </ul>
 *
 * @author Marcus Hert Da Coregio
 * @author Andy Wilkinson
 */
final class ForceSnapshots implements Action<DependencyResolveDetails> {

	private static final Set<String> IGNORED_GROUPS = Set.of("org.springframework.boot", "org.springframework.cloud",
			"org.springframework.plugin", "org.springframework");

	@Override
	public void execute(DependencyResolveDetails dependency) {
		ModuleVersionSelector requested = dependency.getRequested();
		String version = requested.getVersion();
		if (version == null || version.isBlank()) {
			return;
		}
		String group = requested.getGroup();
		if (group.startsWith("org.springframework") && !IGNORED_GROUPS.contains(group)
				&& !requested.getName().equals("spring-security-rsa")) {
			dependency.useVersion(snapshotOf(version));
		}
	}

	private String snapshotOf(String version) {
		if (version.endsWith("-SNAPSHOT")) {
			return version;
		}
		boolean oldVersionFormat = version.matches(".*\\.(((M|RC)\\d+)|RELEASE)$");
		if (oldVersionFormat) {
			return buildSnapshotOf(version);
		}
		boolean isMilestone = version.matches(".*-(M|RC)\\d+$");
		String rawVersion = version.split("-")[0];
		if (isMilestone) {
			return rawVersion + "-SNAPSHOT";
		}
		String[] parts = rawVersion.split("\\.");
		int nextPatchVersion = Integer.parseInt(parts[2]) + 1;
		parts[2] = nextPatchVersion + "-SNAPSHOT";
		return String.join(".", parts);
	}

	private String buildSnapshotOf(String version) {
		boolean isMilestone = !version.endsWith(".RELEASE");
		String rawVersion = version.substring(0, version.lastIndexOf("."));
		if (isMilestone) {
			return rawVersion + ".BUILD-SNAPSHOT";
		}
		String[] parts = rawVersion.split("\\.");
		int nextPatchVersion = Integer.parseInt(parts[2]) + 1;
		parts[2] = nextPatchVersion + ".BUILD-SNAPSHOT";
		return String.join(".", parts);
	}

}
