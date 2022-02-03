package com.example.spring_batch_partitioner.partitioner;

import com.example.spring_batch_partitioner.emtity.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ProductIdRangePartitioner implements Partitioner {

	private final ProductRepository productRepository;
	private final LocalDate startDate;
	private final LocalDate endDate;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		long min = productRepository.findMinId(startDate, endDate);
		long max = productRepository.findMaxId(startDate, endDate);
		long targetSize = (max - min) / gridSize + 1;

		Map<String, ExecutionContext> result = new HashMap<>();
		long number = 0;
		long start = min;
		long end = start + targetSize - 1;

		while (start <= max) {
			ExecutionContext value = new ExecutionContext();
			result.put("partition" + number, value);

			if (end >= max) {
				end = max;
			}

			value.putLong("minId", start);
			value.putLong("maxId", end);
			start += targetSize;
			end += targetSize;
			number++;
		}
		return result;
	}
}














