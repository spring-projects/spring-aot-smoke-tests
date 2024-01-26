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

package org.springframework.aot.gradle.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import org.springframework.aot.gradle.SmokeTest;

/**
 * Task to update the GitHub Actions workflow for a smoke test.
 *
 * @author Andy Wilkinson
 */
public abstract class GenerateGitHubActionsWorkflow extends DefaultTask {

	public GenerateGitHubActionsWorkflow() {
		getOutputFile().convention(getProject().getLayout()
			.getBuildDirectory()
			.file(getSmokeTest()
				.map((smokeTest) -> "workflows/" + smokeTest.group() + "-" + smokeTest.name() + ".yml")));
	}

	@OutputFile
	public abstract RegularFileProperty getOutputFile();

	@Input
	public abstract Property<SmokeTest> getSmokeTest();

	@TaskAction
	void updateWorkflow() {
		SmokeTest smokeTest = getSmokeTest().get();
		File workflowFile = getOutputFile().getAsFile().get();
		String workflowName = name(smokeTest.group()) + " Smoke Tests | " + name(smokeTest.name());
		try (PrintWriter writer = new PrintWriter(new FileWriter(workflowFile))) {
			writer.println("name: " + workflowName);
			writer.println("on:");
			writer.println("  schedule:");
			writer.println("    - cron : '0 0 * * *'");
			writer.println("jobs:");
			if (smokeTest.appTests()) {
				writer.println("  " + jobId(smokeTest.name() + "_app_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " App Test");
				writer.println("    uses: ./.github/workflows/smoke-test-jvm.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: appTest");
				writer.println("  " + jobId(smokeTest.name() + "_native_app_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " Native App Test");
				writer.println("    uses: ./.github/workflows/smoke-test-native.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: nativeAppTest");
			}
			if (smokeTest.tests()) {
				writer.println("  " + jobId(smokeTest.name() + "_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " Test");
				writer.println("    uses: ./.github/workflows/smoke-test-jvm.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: test");
				writer.println("  " + jobId(smokeTest.name() + "_native_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " Native Test");
				writer.println("    uses: ./.github/workflows/smoke-test-native.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: nativeTest");
			}
		}
		catch (IOException ex) {
			throw new GradleException("Failed to write workflow file '" + workflowFile + "'", ex);
		}
	}

	private String jobId(String input) {
		return input.replace("-", "_");
	}

	private String name(String input) {
		StringBuilder output = new StringBuilder(input.length());
		char previous = ' ';
		for (char c : input.replace("-", " ").toCharArray()) {
			if (previous == ' ') {
				output.append(Character.toUpperCase(c));
			}
			else {
				output.append(c);
			}
			previous = c;
		}
		return output.toString();
	}

}
