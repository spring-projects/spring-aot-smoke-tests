package com.example.actuator.webflux.mgmtport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.context.ReactiveWebServerInitializedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ActuatorWebFluxMgmtPortApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ActuatorWebFluxMgmtPortApplication.class, args);
		Thread.currentThread().join(); // To be able to measure memory consumption
	}

	@EventListener
	public void onAppEvent(ReactiveWebServerInitializedEvent e) {
		// This is needed to get the management port in the AOT tests
		if ("management".equals(e.getApplicationContext().getServerNamespace())) {
			System.out.printf("Management port: %d%n", e.getWebServer().getPort());
		}
	}

}
