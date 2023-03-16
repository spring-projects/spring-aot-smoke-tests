package com.example.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QuartzConfiguration {

	@Bean
	@RegisterReflectionForBinding(SimpleJob.class)
	public JobDetail simpleJobDetail() {
		return JobBuilder.newJob(SimpleJob.class)
			.withIdentity("simpleJob")
			.usingJobData("greeting", "Hello world!")
			.storeDurably()
			.build();
	}

	@Bean
	Trigger simpleJobTrigger(JobDetail simpleJobDetail) {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1);
		return TriggerBuilder.newTrigger()
			.forJob(simpleJobDetail)
			.withIdentity("simpleJobTrigger")
			.withSchedule(scheduleBuilder)
			.build();
	}

}
