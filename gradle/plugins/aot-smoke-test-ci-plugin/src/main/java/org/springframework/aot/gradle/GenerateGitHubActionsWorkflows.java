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

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Task to generate the GitHub Actions workflows for the smoke tests.
 *
 * @author Andy Wilkinson
 */
public abstract class GenerateGitHubActionsWorkflows extends DefaultTask {

	private static final String GITHUB_REPOSITORY = "spring-projects/spring-aot-smoke-tests";

	private static final String OWNER_SECRET_SUFFIX = "_OWNER_IDS";

	@OutputDirectory
	public abstract DirectoryProperty getOutputDirectory();

	@Input
	public abstract ListProperty<SmokeTest> getSmokeTests();

	@Input
	public abstract Property<String> getGitBranch();

	@Input
	public abstract Property<String> getCronSchedule();

	@Input
	public abstract Property<String> getSpringBootGeneration();

	public GenerateGitHubActionsWorkflows() {
		getGitBranch().convention(getSpringBootGeneration());
		getOutputDirectory().convention(getSpringBootGeneration()
			.flatMap((generation) -> getProject().getLayout().getBuildDirectory().dir("workflows/" + generation)));
	}

	@TaskAction
	void generateWorkflows() {
		getProject().delete(getOutputDirectory());
		getSmokeTests().get().forEach(this::generateWorkflow);
	}

	void generateWorkflow(SmokeTest smokeTest) {
		File workflowFile = getOutputDirectory()
			.file(getSpringBootGeneration().get() + "-" + smokeTest.group() + "-" + smokeTest.name() + ".yml")
			.get()
			.getAsFile();
		workflowFile.getParentFile().mkdirs();
		String workflowName = getSpringBootGeneration().get() + " | " + name(smokeTest.group()) + " Smoke Tests | "
				+ name(smokeTest.name());
		String groupOwnersSecretName = getOwnerSecretName(smokeTest.group());
		String testOwnersSecretName = getOwnerSecretName(smokeTest.name());
		try (PrintWriter writer = new PrintWriter(new FileWriter(workflowFile))) {
			writer.println("name: " + workflowName);
			writer.println("on:");
			writer.println("  schedule:");
			writer.println("    - cron : '" + getCronSchedule().get() + "'");
			writer.println("  workflow_dispatch:");
			writer.println("jobs:");
			if (smokeTest.appTests()) {
				writer.println("  " + jobId(smokeTest.name() + "_app_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " App Test");
				writer.println("    uses: ./.github/workflows/smoke-test-jvm.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      checkout_repository: " + GITHUB_REPOSITORY);
				writer.println("      checkout_ref: " + getGitBranch().get());
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: appTest");
				writer.println("      group_owners_secret_name: " + groupOwnersSecretName);
				writer.println("      test_owners_secret_name: " + testOwnersSecretName);
				writer.println("  " + jobId(smokeTest.name() + "_native_app_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " Native App Test");
				writer.println("    uses: ./.github/workflows/smoke-test-native.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      checkout_repository: " + GITHUB_REPOSITORY);
				writer.println("      checkout_ref: " + getGitBranch().get());
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: nativeAppTest");
				writer.println("      group_owners_secret_name: " + groupOwnersSecretName);
				writer.println("      test_owners_secret_name: " + testOwnersSecretName);
			}
			if (smokeTest.tests()) {
				writer.println("  " + jobId(smokeTest.name() + "_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " Test");
				writer.println("    uses: ./.github/workflows/smoke-test-jvm.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      checkout_repository: " + GITHUB_REPOSITORY);
				writer.println("      checkout_ref: " + getGitBranch().get());
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: test");
				writer.println("      group_owners_secret_name: " + groupOwnersSecretName);
				writer.println("      test_owners_secret_name: " + testOwnersSecretName);
				writer.println("  " + jobId(smokeTest.name() + "_native_test") + ":");
				writer.println("    name: " + name(smokeTest.name()) + " Native Test");
				writer.println("    uses: ./.github/workflows/smoke-test-native.yml");
				writer.println("    secrets: inherit");
				writer.println("    with:");
				writer.println("      checkout_repository: " + GITHUB_REPOSITORY);
				writer.println("      checkout_ref: " + getGitBranch().get());
				writer.println("      project: " + smokeTest.group() + ":" + smokeTest.name());
				writer.println("      task: nativeTest");
				writer.println("      group_owners_secret_name: " + groupOwnersSecretName);
				writer.println("      test_owners_secret_name: " + testOwnersSecretName);
			}
		}
		catch (IOException ex) {
			throw new GradleException("Failed to write workflow file '" + workflowFile + "'", ex);
		}
	}

	private String jobId(String input) {
		return input.replace("-", "_");
	}

	private String getOwnerSecretName(String value) {
		return value.toUpperCase().replaceAll("-", "_") + OWNER_SECRET_SUFFIX;
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