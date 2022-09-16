package com.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration(proxyBeanMethods = false)
@EnableBatchProcessing
class BatchConfiguration {

	@Bean
	public Job job(JobRepository jobRepository, Step step1) {
		return new JobBuilder("job").repository(jobRepository).start(step1).build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1").tasklet((stepContribution, chunkContext) -> {
			System.out.println("Step 1 running");
			return RepeatStatus.FINISHED;
		}).repository(jobRepository).transactionManager(transactionManager).build();
	}

}
