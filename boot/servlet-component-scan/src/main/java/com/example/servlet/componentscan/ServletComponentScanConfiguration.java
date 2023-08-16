package com.example.servlet.componentscan;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ServletComponentScan
class ServletComponentScanConfiguration {

}
