package com.example.servlet.tomcat;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ServletConfiguration {

	@Bean
	ServletRegistrationBean<TestServlet> testServletServletRegistrationBean() {
		return new ServletRegistrationBean<>(new TestServlet(), "/*");
	}

}
