package com.example.tcf;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(MyServiceConfiguration.class)
class MockBeanTests {

	@MockBean
	private MyService myService;

	@Test
	void shouldGetMock() {
		Mockito.doThrow(new RuntimeException("Boom from mock")).when(myService).sayHello();
		assertThatThrownBy(() -> myService.sayHello()).hasMessage("Boom from mock");
	}

}
