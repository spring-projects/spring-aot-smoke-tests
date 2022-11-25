package com.example.boot.tcf;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MockBeanTests {

	@MockBean
	private MyComponentImpl myComponentImpl;

	@MockBean
	private MyComponentInterface myComponentInterface;

	@Test
	void shouldMockComponent() {
		Mockito.when(myComponentImpl.value()).thenReturn(2);
		assertThat(myComponentImpl.value()).isEqualTo(2);
	}

	@Test
	void shouldMockInterface() {
		Mockito.when(myComponentInterface.value()).thenReturn(2);
		assertThat(myComponentInterface.value()).isEqualTo(2);
	}

}
