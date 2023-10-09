package com.stroganov.warehouse.domain.dto.statistics;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StatisticsDTO {
    private double totalCostGoodsInStock;
    private double totalSellGoodsInStock;
    private long totalStockAmount;
    private long totalStockWithZeroAmount;
    private long totalStockWithAmountLessThenFive;
    private List<StatisticsByManufacture> statisticsByManufactures = new ArrayList<>();
}
