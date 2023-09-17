package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    Optional<Warehouse> findByWarehouseNameAndAddress(String warehouseName, String address);
}
