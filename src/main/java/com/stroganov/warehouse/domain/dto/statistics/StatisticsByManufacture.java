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
    String totalCostGoodsInStock;
    String totalSellGoodsInStock;
    int totalItemsAmount;
}
