package com.example.configprops;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.aot.smoketest.support.assertj.AssertableOutput;
import org.springframework.aot.smoketest.support.junit.AotSmokeTest;

import static org.assertj.core.api.Assertions.assertThat;

@AotSmokeTest
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

	}

	@Test
	void shouldImportConfig(AssertableOutput output) {
		Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
			assertThat(output).hasSingleLineContaining("test.yaml some.imported.key: some-value");
		});

	}

}
