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
