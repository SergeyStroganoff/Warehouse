package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.domain.model.warehouse.Stock;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import com.stroganov.warehouse.repository.StockRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {  //TODO

    @Autowired
    public Logger logger;
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private StockRepository stockRepository;

    @Override
    @Transactional
    public void stockInitialisation(Iterable<Item> item, int startAmount) {
        Assert.notNull(item, "Error, item list can't be NULL");
        List<Warehouse> warehouses = warehouseService.getUserWarehouseList();
        Assert.notEmpty(warehouses, "Warehouse list can't be empty");
        Warehouse defaultWarehouse = warehouses.get(0);
        Optional<Warehouse> warehouseOptional = warehouseService.findById(defaultWarehouse.getId());
        if (warehouseOptional.isPresent()) {
            List<Stock> stockList = new ArrayList<>();
            for (Item currentItem : item) {
                Stock stock = new Stock(0, currentItem, 0, warehouseOptional.get());
                stockList.add(stock);
            }
            stockRepository.saveAll(stockList);
        } else {
            logger.error("Warehouse was not found: " + defaultWarehouse);
            throw new RuntimeException("Warehouse was not found");
        }
    }

    @Override
    public void saveAll(List<Stock> stockList) {
        stockRepository.saveAll(stockList);
    }
}
