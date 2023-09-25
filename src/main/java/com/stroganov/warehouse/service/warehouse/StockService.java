package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.domain.model.warehouse.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {
    void stockInitialisation(Iterable<Item> item, int startAmount);

    Optional<Stock> getCurrentStockByItemParams(String modelArticle, String manufactureName, String styleArticle);

    Optional<Integer> findStockIdByModelArticleProducerNameStyleArticle(
            String modelArticle, String producerName, String styleArticle);

    void saveAll(List<Stock> stockList);
}
