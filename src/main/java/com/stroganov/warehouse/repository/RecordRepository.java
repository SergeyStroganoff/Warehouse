package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.transaction.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
