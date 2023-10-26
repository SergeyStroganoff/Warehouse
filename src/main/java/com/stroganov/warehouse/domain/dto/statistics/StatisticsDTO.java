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
    private String totalCostGoodsInStock;
    private String totalSellGoodsInStock; //todo
    private long totalStockAmount;
    private long totalStockWithZeroAmount;
    private long totalStockWithAmountLessThenFive;
    private List<StatisticsByManufacture> statisticsByManufactures = new ArrayList<>();
}
