package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.domain.model.warehouse.Stock;

import java.util.List;

public interface StockService {
    void stockInitialisation(Iterable<Item> item, int startAmount);
    void saveAll(List<Stock> stockList);
}
