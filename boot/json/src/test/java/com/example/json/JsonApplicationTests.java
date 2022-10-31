package com.example.json;

import java.io.IOException;

import com.example.json.model.Dto;
import com.example.json.model.Dto2;
import com.example.json.model.Dto3;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class JsonApplicationTests {

	@Autowired
	private JacksonTester<Dto> dtoTester;

	@Autowired
	private JacksonTester<Dto2> dto2Tester;

	@Autowired
	private JacksonTester<Dto3> dto3Tester;

	@Test
	void serializeDto() throws IOException {
		assertThat(this.dtoTester.write(getDto())).isStrictlyEqualToJson(getDtoJson());
	}

	@Test
	void deserializeDto() throws IOException {
		assertThat(this.dtoTester.parse(getDtoJson()).getObject()).isEqualTo(getDto());
	}

	@Test
	void serializeDto2() throws IOException {
		assertThat(this.dto2Tester.write(getDto2())).isStrictlyEqualToJson(getDto2Json());
	}

	@Test
	void deserializeDto2() throws IOException {
		assertThat(this.dto2Tester.parse(getDto2Json()).getObject()).isEqualTo(getDto2());
	}

	@Test
	void serializeDto3() throws IOException {
		assertThat(this.dto3Tester.write(getDto3())).isStrictlyEqualToJson(getDto3Json());
	}

	@Test
	void deserializeDto3() throws IOException {
		assertThat(this.dto3Tester.parse(getDto3Json()).getObject()).isEqualTo(getDto3());
	}

	private static Dto getDto() {
		return new Dto("field-1");
	}

	private static String getDtoJson() {
		return """
				{"field":"field-1"}
				""";
	}

	private static Dto2 getDto2() {
		return new Dto2("field-2");
	}

	private static String getDto2Json() {
		return """
				{"customized-field":"field-2"}
				""";
	}

	private static Dto3 getDto3() {
		return new Dto3("field-3");
	}

	private static String getDto3Json() {
		return """
				{"mixin-field":"field-3"}
				""";
	}

}
