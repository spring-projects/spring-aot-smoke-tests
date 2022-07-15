package com.example.cloud.task;

import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.listener.annotation.FailedTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.stereotype.Component;

@Component
public class Listener {

	@BeforeTask
	public void beforeTask(TaskExecution taskExecution) {
		System.out.printf("Before task: %s%n", taskExecution.getTaskName());
	}

	@AfterTask
	public void afterTask(TaskExecution taskExecution) {
		System.out.printf("After task: %s%n", taskExecution.getTaskName());
	}

	@FailedTask
	public void failedTask(TaskExecution taskExecution, Throwable throwable) {
		System.out.printf("Failed task: %s: %s%n", taskExecution.getTaskName(), throwable);
	}

}
