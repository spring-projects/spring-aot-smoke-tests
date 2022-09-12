package com.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration(proxyBeanMethods = false)
@EnableBatchProcessing
class BatchConfiguration {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, Step step1) {
		return jobBuilderFactory.get("job").start(step1).build();
	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, PlatformTransactionManager transactionManager) {
		return stepBuilderFactory.get("step1").tasklet((stepContribution, chunkContext) -> {
			System.out.println("Step 1 running");
			return RepeatStatus.FINISHED;
		}).transactionManager(transactionManager).build();
	}

}
