package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.dto.statistics.StatisticsByManufacture;

import java.util.List;

public interface StatisticsService {
    long getTotalAmountOfStockByWarehouseId(int warehouseId);

    long getStockByWarehouseAndAmountZero(int warehouseId);

    long getStockByWarehouseAndAmountLessThan(int warehouseId, int amountLess);

    double getTotalWarehouseSellPrice(int warehouseId);

    List<StatisticsByManufacture> getStatisticsByProducer(int warehouseId);
}
