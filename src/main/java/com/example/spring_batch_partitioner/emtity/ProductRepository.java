package com.example.spring_batch_partitioner.emtity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Transactional()
	@Query("SELECT p.name " +
		"FROM Product p " +
			"WHERE p.createDate =: createDate")
	List<String> findAllByCreateDate(@Param("createDate")LocalDate createDate);

	@Query("SELECT MAX(p.id) " +
		"FROM Product p " +
		"WHERE p.createDate BETWEEN :startDate AND :endDate")
	Long findMaxId(@Param("startDate") LocalDate startDate,
	               @Param("endDate") LocalDate endDate);

	@Query("SELECT MIN(p.id) " +
		"FROM Product p " +
		"WHERE p.createDate BETWEEN :startDate AND :endDate")
	Long findMinId(@Param("startDate") LocalDate startDate,
	               @Param("endDate") LocalDate endDate);
}
