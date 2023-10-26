package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.dto.statistics.StatisticsByManufacture;
import com.stroganov.warehouse.domain.dto.statistics.StatisticsDTO;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import com.stroganov.warehouse.service.warehouse.StatisticsService;
import com.stroganov.warehouse.service.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class StatisticsController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private DecimalFormat decimalFormat;

    @GetMapping("/statistics-form")
    public String getAllStockPage(Model model) {
        Warehouse warehouse = warehouseService.getCurrentUserWarehouseList().get(0);
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        long totalAmountOfStock = statisticsService.getTotalAmountOfStockByWarehouseId(warehouse.getId());
        long amountOfStockWithZeroStock = statisticsService.getStockByWarehouseAndAmountZero(warehouse.getId());
        long amountOfStockWithStockLessThanFive = statisticsService.getStockByWarehouseAndAmountLessThan(warehouse.getId(), 5);
        double totalWarehouseSellPrice = statisticsService.getTotalWarehouseSellPrice(warehouse.getId());
        List<StatisticsByManufacture> statisticsByManufactureList = statisticsService.getStatisticsByProducer(warehouse.getId());
        statisticsDTO.setStatisticsByManufactures(statisticsByManufactureList);
        statisticsDTO.setTotalCostGoodsInStock(decimalFormat.format(totalWarehouseSellPrice));
        statisticsDTO.setTotalStockAmount(totalAmountOfStock);
        statisticsDTO.setTotalStockWithZeroAmount(amountOfStockWithZeroStock);
        statisticsDTO.setTotalSellGoodsInStock(String.valueOf(totalAmountOfStock - amountOfStockWithZeroStock));
        statisticsDTO.setTotalStockWithAmountLessThenFive(amountOfStockWithStockLessThanFive);
        model.addAttribute("statistics", statisticsDTO);
        return "stat";
    }
}
