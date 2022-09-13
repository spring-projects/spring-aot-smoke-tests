package com.example.batch;

import javax.sql.DataSource;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration(proxyBeanMethods = false)
class BatchConfiguration {

	@Bean
	@StepScope
	public FlatFileItemReader<Person> reader(@Value("#{jobParameters['fileName']}") String fileName) {
		return new FlatFileItemReaderBuilder<Person>().name("personItemReader")
				.resource(new ClassPathResource(fileName)).delimited().names("firstName", "lastName")
				.fieldSetMapper(new RecordFieldSetMapper<>(Person.class)).build();
	}

	@Bean
	@JobScope // Not really needed, but just to test native support for job scoped Lambdas
	@RegisterReflectionForBinding(Person.class)
	public ItemProcessor<Person, Person> processor() {
		return item -> new Person(item.firstName().toUpperCase(), item.lastName().toUpperCase());

	}

	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO person (first_name, last_name) VALUES (:firstName, :lastName)").dataSource(dataSource)
				.build();
	}

	@Bean
	public JobCompletionNotificationListener notificationListener(JdbcTemplate jdbcTemplate) {
		return new JobCompletionNotificationListener(jdbcTemplate);
	}

	@Bean
	public Step step1(JobRepository jobRepository, FlatFileItemReader<Person> itemReader,
			ItemProcessor<Person, Person> itemProcessor, JdbcBatchItemWriter<Person> itemWriter,
			PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", jobRepository).<Person, Person>chunk(2, transactionManager).reader(itemReader)
				.processor(itemProcessor).writer(itemWriter).build();
	}

	@Bean
	public Job job(JobRepository jobRepository, Step step, JobCompletionNotificationListener listener) {
		return new JobBuilder("job", jobRepository).start(step).listener(listener).build();
	}

}
