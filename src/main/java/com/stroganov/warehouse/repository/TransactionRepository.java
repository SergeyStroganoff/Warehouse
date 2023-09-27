package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
