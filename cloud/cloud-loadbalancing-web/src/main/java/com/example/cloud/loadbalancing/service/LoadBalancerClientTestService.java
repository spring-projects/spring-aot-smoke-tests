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

package com.example.cloud.loadbalancing.service;

import com.example.cloud.loadbalancing.client.DemoServiceClient;
import com.example.cloud.loadbalancing.client.TestServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

@Component
public class LoadBalancerClientTestService {

	private static final Log LOG = LogFactory.getLog(LoadBalancerClientTestService.class);

	private final TestServiceClient testServiceClient;

	private final DemoServiceClient demoServiceClient;

	public LoadBalancerClientTestService(TestServiceClient testServiceClient, DemoServiceClient demoServiceClient) {
		this.testServiceClient = testServiceClient;
		this.demoServiceClient = demoServiceClient;

	}

	public void callServices() {
		if (LOG.isInfoEnabled()) {
			LOG.info(testServiceClient.test());
		}
		if (LOG.isInfoEnabled()) {
			LOG.info(demoServiceClient.demo());
		}
	}

}
