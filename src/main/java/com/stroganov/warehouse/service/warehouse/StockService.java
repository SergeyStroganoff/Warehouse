package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.domain.model.warehouse.Stock;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StockService {
    void stockInitialisation(Iterable<Item> item, int startAmount);

    Optional<Stock> getCurrentStockByItemParamsAndWarehouseId(String modelArticle, String manufactureName, String styleArticle, int warehouseId);

    Optional<Integer> findStockIdByModelArticleProducerNameStyleArticle(
            String modelArticle, String producerName, String styleArticle);

    boolean isStockExist(String modelArticle, String producerName, String styleArticle, int warehouseId);

    void saveAll(List<Stock> stockList);

    Page<Stock> getPageOfStockByWarehouseId(int page, int size, int warehouseId);

    Page<Stock> getPageOfStockFilteredByManufactureIdAndWarehouseId(Integer manufactureId, int page, int size, int warehouseId);

    Page<Stock> getPageOfStockFilteredByManufactureIdAndByAmountLessAndWarehouseId(Integer producerId, Integer amountLess, int page, int size, int warehouseId);

    Page<Stock> getPageOfStockFilteredByAmountLessAndWarehouseId(Integer amountLess, int page, int size, int warehouseId);
}
