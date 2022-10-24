package com.example.commandlinerunner;

import com.example.commandlinerunner.service.MyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
// https://github.com/spring-projects/spring-boot/issues/32195
@DisabledInNativeImage
class MockBeanTests {

	@MockBean
	private MyService myService;

	@Test
	void shouldGetMock() {
		Mockito.doThrow(new RuntimeException("Boom from mock")).when(myService).sayHello();
		assertThatThrownBy(() -> myService.sayHello()).hasMessage("Boom from mock");
	}

}
