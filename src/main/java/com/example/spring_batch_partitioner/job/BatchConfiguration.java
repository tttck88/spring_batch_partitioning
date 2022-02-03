package com.example.spring_batch_partitioner.job;

import com.example.spring_batch_partitioner.emtity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {
	public static final String JOB_NAME = "partitionLocalBatch";

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private int poolSize;

	@Value("${poolSize:5}")
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	@Bean(name = JOB_NAME + "_partitionHandler")
	public TaskExecutorPartitionHandler partitionHandler() {
		TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
		partitionHandler.setStep(step1());
		partitionHandler.setTaskExecutor(executor());
		partitionHandler.setGridSize(poolsize);
		return partitionHandler;
	}

	@Bean(name = JOB_NAME + "_taskPool")
	public TaskExecutor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(poolSize);
		executor.setMaxPoolSize(poolSize);
		executor.setThreadNamePrefix("partition-thread");
		executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
		executor.initialize();
		return executor;
	}

	@Bean(name = JOB_NAME +"_step1Manager")
	public Step step1Manager() {
		return stepBuilderFactory.get("step1.manager")
			.partitioner("step1", partitioner(null, null))
			.step(step1())
			.partitionHandler(partitionHandler())
			.build();
	}

	@Bean(name = JOB_NAME + "_step")
	public Step step1() {
		return stepBuilderFactory.get(JOB_NAME + "_step")
			.<Product, ProductBackup>chunk(chunkSize)
			.reader(reader(null, null))
			.processor(processor())
			.writer(writer(null,null))
			.build();

	}
}
