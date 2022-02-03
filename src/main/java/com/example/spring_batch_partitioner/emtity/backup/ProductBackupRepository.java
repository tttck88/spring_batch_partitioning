package com.example.spring_batch_partitioner.emtity.backup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBackupRepository extends JpaRepository<ProductBackup, Long> {
}
