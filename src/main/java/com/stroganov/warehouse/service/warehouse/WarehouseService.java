package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.model.warehouse.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    List<Warehouse> getCurrentUserWarehouseList();
    Optional<Warehouse> getCurrentUserWarehouse();
    Optional<Warehouse> findById(int id);
}
