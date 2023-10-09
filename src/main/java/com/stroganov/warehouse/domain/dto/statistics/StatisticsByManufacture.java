package com.stroganov.warehouse.domain.dto.statistics;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StatisticsByManufacture {
    String manufactureName;
    String manufactureDescription;
    double totalCostGoodsInStock;
    double totalSellGoodsInStock;
    int totalItemsAmount;
}
