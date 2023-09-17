package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.warehouse.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
}
