package com.example.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.scheduling.quartz.QuartzJobBean;

class SimpleJob extends QuartzJobBean {

	private String greeting;

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.printf("SimpleJob running: greeting = '%s'%n", this.greeting);
	}

}
