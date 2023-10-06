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
    private Double totalCostGoodsInStock;
    private Double totalSellGoodsInStock;
    private int totalItemsAmount;
    private List<StatisticsByManufacture> statisticsByManufactures = new ArrayList<>();
}
