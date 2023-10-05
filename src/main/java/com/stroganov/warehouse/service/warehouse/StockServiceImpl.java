package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.domain.model.warehouse.Stock;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import com.stroganov.warehouse.repository.StockRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    public Logger logger;
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void stockInitialisation(Iterable<Item> item, int startAmount) {
        Assert.notNull(item, "Error, item list can't be NULL");
        Optional<Warehouse> warehouseOptional = warehouseService.getCurrentUserWarehouse();
        if (warehouseOptional.isPresent()) {
            List<Stock> stockList = new ArrayList<>();
            for (Item currentItem : item) {
                Stock stock = new Stock(0, currentItem, 0, warehouseOptional.get());
                stockList.add(stock);
            }
            stockRepository.saveAll(stockList);
        } else {
            logger.error("Warehouse was not found");
            throw new RuntimeException("Warehouse was not found");
        }
    }

    @Override
    public Optional<Stock> getCurrentStockByItemParamsAndWarehouseId(String modelArticle, String manufactureName, String styleArticle, int warehouseId) {
        return stockRepository.findByItem_Model_ArticleAndItem_Producer_NameAndItem_ItemStyle_StyleArticleAndWarehouse_Id(modelArticle, manufactureName, styleArticle,warehouseId);
    }

    @Override
    public Optional<Integer> findStockIdByModelArticleProducerNameStyleArticle(
            String modelArticle, String producerName, String styleArticle) {
        return Optional.ofNullable(stockRepository.findStockIdByModelArticleProducerNameStyleArticle(
                modelArticle, producerName, styleArticle));
    }

    @Override
    public boolean isStockExist(String modelArticle, String producerName, String styleArticle, int warehouseId) {
        return stockRepository.existsByItem_Model_ArticleAndItem_Producer_NameAndItem_ItemStyle_StyleArticleAndWarehouseId(
                modelArticle, producerName, styleArticle, warehouseId);
    }

    @Override
    public void saveAll(List<Stock> stockList) {
        stockRepository.saveAll(stockList);
    }

    @Override
    public Page<Stock> getPageOfStock(int page, int size) {
        return stockRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Stock> getPageOfStockFilteredByManufactureId(Integer manufactureId, int page, int size) {
        return stockRepository.findByItem_Producer_Id(manufactureId, PageRequest.of(page, size));
    }

    @Override
    public Page<Stock> getPageOfStockFilteredByManufactureIdAndByAmountLess(Integer producerId, Integer amountLess, int page, int size) {
        return stockRepository.findByItem_Producer_IdAndAmountLessThan(producerId, amountLess, PageRequest.of(page, size));
    }

    @Override
    public Page<Stock> getPageOfStockFilteredByAmountLess(Integer amountLess, int page, int size) {
        return stockRepository.findByAmountLessThan(amountLess, PageRequest.of(page, size));
    }
}
