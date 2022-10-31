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
