package com.stroganov.warehouse.domain.dto.statistics;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StatisticsByManufacture {
    private String manufactureName;
    private String manufactureDescription;
    private Double totalCostGoodsInStock;
    private Double totalSellGoodsInStock;
    private int totalItemsAmount;
}
