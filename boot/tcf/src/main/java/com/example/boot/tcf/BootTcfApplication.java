package com.example.boot.tcf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootTcfApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BootTcfApplication.class);
		System.setProperty("main.ran", "true");
		application.run(args);
	}

}
