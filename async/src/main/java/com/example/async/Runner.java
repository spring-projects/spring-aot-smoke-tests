package com.example.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Runner {

	@Async
	public void run() {
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(CLR.PREFIX.get() + "Asynchronous action running...");
	}

}
