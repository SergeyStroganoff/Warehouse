package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.dto.statistics.StatisticsByManufacture;
import com.stroganov.warehouse.repository.StockRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    DecimalFormat decimalFormat;


    @Override
    public long getTotalAmountOfStockByWarehouseId(int warehouseId) {
        return stockRepository.getTotalAmountOfStockByWarehouseId(warehouseId);
    }

    @Override
    public long getStockByWarehouseAndAmountZero(int warehouseId) {
        return stockRepository.getStockByWarehouseAndAmount(warehouseId, 0);
    }

    @Override
    public long getStockByWarehouseAndAmountLessThan(int warehouseId, int amountLess) {
        return stockRepository.getStockByWarehouseAndAmountLessThan(warehouseId, amountLess);
    }

    @Override
    public double getTotalWarehouseSellPrice(int warehouseId) {
        Double totalWarehouseSellPrice = stockRepository.getTotalWarehouseSellPrice(warehouseId);
        return totalWarehouseSellPrice == null ? 0 : totalWarehouseSellPrice;
    }

    @Override
    public List<StatisticsByManufacture> getStatisticsByProducer(int warehouseId) {
        List<Tuple> tuples = stockRepository.getStatisticsByProducer(warehouseId);
        return mapToStatisticsDto(tuples);
    }

    public List<StatisticsByManufacture> mapToStatisticsDto(List<Tuple> tuples) {

        return tuples.stream()
                .map(tuple -> new StatisticsByManufacture(
                        tuple.get("manufactureName", String.class),
                        tuple.get("manufactureDescription", String.class),
                        decimalFormat.format( tuple.get("totalCostGoodsInStock", Double.class)),
                        decimalFormat.format(tuple.get("totalSellGoodsInStock", Double.class)),
                        tuple.get("totalItemsAmount", Long.class).intValue()
                ))
                .collect(Collectors.toList());
    }
}
