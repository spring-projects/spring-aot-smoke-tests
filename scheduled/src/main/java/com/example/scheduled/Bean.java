package com.example.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class Bean {

	@Scheduled(fixedRate = 1000)
	void fixedRate() {
		System.out.println("fixedRate()");
	}

	@Scheduled(fixedDelay = 1000)
	void fixedDelay() {
		System.out.println("fixedDelay()");
	}

	@Scheduled(cron = "*/2 * * * * *")
	void cron() {
		System.out.println("cron()");
	}

}
