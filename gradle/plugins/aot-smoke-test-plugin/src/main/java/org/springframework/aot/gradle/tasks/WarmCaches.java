/*
 * Copyright 2022-2025 the original author or authors.
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

package org.springframework.aot.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.TaskAction;

/**
 * Task to warm Gradle's caches by downloading the configured dependencies.
 *
 * @author Andy Wilkinson
 */
public class WarmCaches extends DefaultTask {

	private final ConfigurableFileCollection dependencies;

	public WarmCaches() {
		this.dependencies = getProject().getObjects().fileCollection();
	}

	public void addDependencies(FileCollection dependencies) {
		this.dependencies.from(dependencies);
	}

	@TaskAction
	void execute() {
		this.dependencies.getFiles();
	}

	@Classpath
	FileCollection getDependencies() {
		return this.dependencies;
	}

}
