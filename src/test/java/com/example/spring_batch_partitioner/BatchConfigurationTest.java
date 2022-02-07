package com.example.spring_batch_partitioner;

import com.example.spring_batch_partitioner.emtity.ProductRepository;
import com.example.spring_batch_partitioner.emtity.backup.ProductBackupRepository;
import com.example.spring_batch_partitioner.job.BatchConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeFormatter;

@Slf4j
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {TestBatchConfig.class, BatchConfiguration.class})
@SpringBatchTest
public class BatchConfigurationTest {
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductBackupRepository productBackupRepository;

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() throws Exception {
		productRepository.deleteAllInBatch();
		productBackupRepository.deleteAllInBatch();
	}

}















