/*
 * Copyright 2022-2024 the original author or authors.
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

package com.example.cloud.openfeign;

import com.example.cloud.openfeign.config.CustomFeignConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
@EnableFeignClients(clients = TestServiceClient.class)
public class CloudOpenFeignApplication implements CommandLineRunner {

	private static final Log LOG = LogFactory.getLog(CloudOpenFeignApplication.class);

	@Autowired
	private TestServiceClient testServiceClient;

	public static void main(String[] args) {
		SpringApplication.run(CloudOpenFeignApplication.class, args);
	}

	@Override
	public void run(String... args) {
		callTestService();
	}

	private void callTestService() {
		if (LOG.isInfoEnabled()) {
			LOG.info(testServiceClient.test());
			LOG.info(testServiceClient.testTypesForReflection(new TestArgType("test type hints")).getTest());
		}
	}

}

@FeignClient(value = "test-service", configuration = CustomFeignConfig.class)
interface TestServiceClient {

	@GetMapping("/")
	String test();

	@PostMapping(value = "/test", produces = "application/json", consumes = "application/json")
	TestReturnType testTypesForReflection(@RequestBody TestArgType testArg);

}

class TestReturnType {

	private String test;

	public TestReturnType(String test) {
		this.test = test;
	}

	public TestReturnType() {
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}

class TestArgType {

	private String test;

	public TestArgType(String test) {
		this.test = test;
	}

	public TestArgType() {
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}
