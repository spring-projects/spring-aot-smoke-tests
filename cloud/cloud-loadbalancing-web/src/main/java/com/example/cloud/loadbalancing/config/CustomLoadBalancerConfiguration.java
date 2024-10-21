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

package com.example.cloud.loadbalancing.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.context.annotation.Bean;

public class CustomLoadBalancerConfiguration {

	@Bean
	LoadBalancerLifecycle<Object, Object, ServiceInstance> loadBalancerLifecycle() {
		return new TestLoadBalancerLifecycle();
	}

	static class TestLoadBalancerLifecycle implements LoadBalancerLifecycle<Object, Object, ServiceInstance> {

		private static final Log LOG = LogFactory.getLog(TestLoadBalancerLifecycle.class);

		@Override
		public void onStart(Request<Object> request) {
			if (LOG.isInfoEnabled()) {
				LOG.info("On Start: " + request);
			}
		}

		@Override
		public void onStartRequest(Request<Object> request, Response<ServiceInstance> lbResponse) {
			if (LOG.isInfoEnabled()) {
				LOG.info("On Start Request: " + request + ", LB response: " + lbResponse);
			}
		}

		@Override
		public void onComplete(CompletionContext<Object, ServiceInstance, Object> completionContext) {
			if (LOG.isInfoEnabled()) {
				LOG.info("On Complete: " + completionContext);
			}
		}

	}

}
