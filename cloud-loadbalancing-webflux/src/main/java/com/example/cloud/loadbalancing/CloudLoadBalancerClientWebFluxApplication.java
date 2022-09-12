package com.example.cloud.loadbalancing;

import com.example.cloud.loadbalancing.service.LoadBalancerClientTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CloudLoadBalancerClientWebFluxApplication implements CommandLineRunner {

	@Autowired
	LoadBalancerClientTestService testService;

	public static void main(String[] args) {
		SpringApplication.run(CloudLoadBalancerClientWebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		testService.callServices();
	}

	@RestController
	static class TestController {

		@GetMapping("/")
		public String test() {
			return "test";
		}

	}

}
