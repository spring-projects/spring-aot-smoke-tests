/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
