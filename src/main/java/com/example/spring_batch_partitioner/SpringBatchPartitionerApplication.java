package com.example.spring_batch_partitioner;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchPartitionerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchPartitionerApplication.class, args);
	}

}
