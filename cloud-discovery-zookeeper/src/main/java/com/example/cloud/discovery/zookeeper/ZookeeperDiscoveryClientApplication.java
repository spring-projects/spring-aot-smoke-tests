package com.example.cloud.discovery.zookeeper;

import org.springframework.aot.smoketest.thirdpartyhints.NettyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ImportRuntimeHints(NettyRuntimeHints.class)
@SpringBootApplication
public class ZookeeperDiscoveryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZookeeperDiscoveryClientApplication.class, args);
	}

	@RestController
	static class TestController {

		@GetMapping("/")
		public String test() {
			return "test";
		}

	}

}
