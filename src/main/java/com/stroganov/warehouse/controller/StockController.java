package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.model.item.Manufacture;
import com.stroganov.warehouse.domain.model.warehouse.Stock;
import com.stroganov.warehouse.service.item.ManufactureService;
import com.stroganov.warehouse.service.warehouse.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StockController {

    static final String STOCK_PAGE = "/all-stock-form";

    @Autowired
    private ManufactureService manufactureService;

    @Autowired
    private StockService stockService;

    @GetMapping("/stock")
    public String getAllStockPage(Model model,
                                  @RequestParam(required = false) Integer producerId,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return getStockPage(model, producerId, page, size);
    }

    @PostMapping("/stock-filtered")
    public String getFilteredStock(Model model,
                                   @ModelAttribute("producer") Integer producerId,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size) {
        return getStockPage(model, producerId, page, size);
    }

    private String getStockPage(Model model, Integer producerId, int page, int size) {
        List<Manufacture> manufactureList = manufactureService.findAllManufacture();
        model.addAttribute(manufactureList);
        Page<Stock> stockPage;
        if (producerId == null) {
            stockPage = stockService.getPageOfStock(page, size);
        } else {
            stockPage = stockService.getPageOfStockFilteredByManufactureId(producerId, page, size);
        }
        model.addAttribute("stocks", stockPage);
        model.addAttribute("producer", producerId);
        return STOCK_PAGE;
    }
}
