package com.example.boot.tcf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class OutputCaptureTests {

	@Test
	void test(CapturedOutput output) {
		System.out.println("Hello World!");
		assertThat(output).contains("Hello World!");
	}

}
