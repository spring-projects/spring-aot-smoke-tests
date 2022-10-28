package com.example.commandlinerunner;

import com.example.commandlinerunner.service.MyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MockBeanTests {

	@MockBean
	private MyService myService;

	@Test
	void shouldGetMock() {
		Mockito.doThrow(new RuntimeException("Boom from mock")).when(myService).sayHello();
		assertThatThrownBy(() -> myService.sayHello()).hasMessage("Boom from mock");
	}

}
