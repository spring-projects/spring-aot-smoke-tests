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

package com.example.configprops;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
class ConfigPropsApplicationAotTests {

	@Nested
	class AppProperties {

		@Test
		void stringShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getString(): a-string");
			});
		}

		@Test
		void dataSizeShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getDataSize(): 1048576B");
			});
		}

		@Test
		void dataSizeWithCustomDefaultUnitShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getCustomDefaultUnitDataSize(): 5242880B");
			});
		}

		@Test
		void durationShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getDuration(): PT30S");
			});
		}

		@Test
		void durationWithCustomDefaultUnitShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getCustomDefaultUnitDuration(): PT10M");
			});
		}

		@Test
		void stringListShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getStringList(): [string-1, string-2]");
			});
		}

		@Test
		void nestedListShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output)
					.hasSingleLineContaining("appProperties.getNestedList(): [Nested{aInt=1}, Nested{aInt=2}]");
			});
		}

		@Test
		void stringMapShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getStringMap(): {a=alpha, b=bravo}");
			});
		}

		@Test
		void nestedMapShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output)
					.hasSingleLineContaining("appProperties.getNestedMap(): {a=Nested{aInt=5}, b=Nested{aInt=6}}");
			});
		}

		@Test
		void nestedShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getNested(): Nested{aInt=3}");
			});
		}

		@Test
		void nestedIntShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getNested().getaInt(): 3");
			});
		}

		@Test
		void nestedNotInnerShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getNestedNotInner(): NestedNotInner{aInt=4}");
			});
		}

		@Test
		void nestedNotInnerIntShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appProperties.getNestedNotInner().getaInt(): 4");
			});
		}

	}

	@Nested
	class AppPropertiesCtor {

		@Test
		void stringShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getString(): a-string");
			});
		}

		@Test
		void dataSizeShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getDataSize(): 1048576B");
			});
		}

		@Test
		void dataSizeWithCustomDefaultUnitShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output)
					.hasSingleLineContaining("appPropertiesCtor.getCustomDefaultUnitDataSize(): 5242880B");
			});
		}

		@Test
		void durationShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getDuration(): PT30S");
			});
		}

		@Test
		void durationWithCustomDefaultUnitShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getCustomDefaultUnitDuration(): PT10M");
			});
		}

		@Test
		void stringListShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getStringList(): [string-1, string-2]");
			});
		}

		@Test
		void nestedListShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output)
					.hasSingleLineContaining("appPropertiesCtor.getNestedList(): [Nested{aInt=1}, Nested{aInt=2}]");
			});
		}

		@Test
		void nestedShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getNested(): Nested{aInt=3}");
			});
		}

		@Test
		void nestedIntShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getNested().getaInt(): 3");
			});
		}

		@Test
		void nestedNotInnerShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output)
					.hasSingleLineContaining("appPropertiesCtor.getNestedNotInner(): NestedNotInner{aInt=4}");
			});
		}

		@Test
		void nestedNotInnerIntShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesCtor.getNestedNotInner().getaInt(): 4");
			});
		}

	}

	@Nested
	class AppPropertiesRecord {

		@Test
		void stringShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.string(): a-string");
			});
		}

		@Test
		void dataSizeShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.dataSize(): 1048576B");
			});
		}

		@Test
		void dataSizeWithCustomDefaultUnitShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.customDefaultUnitDataSize(): 5242880B");
			});
		}

		@Test
		void durationShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.duration(): PT30S");
			});
		}

		@Test
		void durationWithCustomDefaultUnitShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.customDefaultUnitDuration(): PT10M");
			});
		}

		@Test
		void stringListShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.stringList(): [string-1, string-2]");
			});
		}

		@Test
		void nestedListShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output)
					.hasSingleLineContaining("appPropertiesRecord.nestedList(): [Nested[aInt=1], Nested[aInt=2]]");
			});
		}

		@Test
		void nestedShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.nested(): Nested[aInt=3]");
			});
		}

		@Test
		void nestedIntShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.nested().aInt(): 3");
			});
		}

		@Test
		void nestedNotInnerShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output)
					.hasSingleLineContaining("appPropertiesRecord.nestedNotInner(): NestedNotInner{aInt=4}");
			});
		}

		@Test
		void nestedNotInnerIntShouldBind(AssertableOutput output) {
			Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
				assertThat(output).hasSingleLineContaining("appPropertiesRecord.nestedNotInner().getaInt(): 4");
			});
		}

	}

	@Test
	void shouldImportConfig(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("test.yaml some.imported.key: some-value");
		});

	}

	@Test
	void shouldNotPrintBanner(AssertableOutput output) {
		Awaitility.await()
			.atMost(Duration.ofSeconds(10))
			.untilAsserted(() -> assertThat(output).hasSingleLineContaining("Started ConfigPropsApplication in"));
		assertThat(output).hasNoLinesContaining(":: Spring Boot ::");
	}

}
