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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class StockController {

    static final String STOCK_PAGE = "/all-stock-form";

    @Autowired
    private ManufactureService manufactureService;

    @Autowired
    private StockService stockService;

    @GetMapping("/stock")
    public String getAllStockPage(Model model,
                                  @RequestParam(required = false) Integer amountLess,
                                  @RequestParam(required = false) Integer producerId,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return getStockPage(model, producerId, amountLess, page, size);
    }

    @PostMapping("/stock-filtered")
    public String getFilteredStock(Model model,
                                   @ModelAttribute("amount") Integer amountLess,
                                   @ModelAttribute("producer") Integer producerId,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size) {
        return getStockPage(model, producerId, amountLess, page, size);
    }

    private String getStockPage(Model model, Integer producerId, Integer amountLess, int page, int size) {
        page = Math.max(page, 0);
        List<Manufacture> manufactureList = manufactureService.findAllManufacture();
        manufactureList.add(new Manufacture(-1, "All", ""));
        manufactureList.sort(Comparator.comparing(Manufacture::getId));
        TreeMap<Integer, String> amountOptions = new TreeMap<>(Map.of(1, "less than 1", 5, "less than 5", 15, "less than 15", 0, "ALL"));
        Page<Stock> stockPage;
        if (producerId != null && producerId > -1) {
            if (amountLess != null && amountLess != 0) {
                stockPage = stockService.getPageOfStockFilteredByManufactureIdAndByAmountLess(producerId, amountLess, page, size);
            } else {
                stockPage = stockService.getPageOfStockFilteredByManufactureId(producerId, page, size);
            }
        } else {
            if (amountLess != null && amountLess != 0) {
                stockPage = stockService.getPageOfStockFilteredByAmountLess(amountLess, page, size);
            } else {
                stockPage = stockService.getPageOfStock(page, size);
            }
        }
        model.addAttribute(manufactureList);
        model.addAttribute("stocks", stockPage);
        model.addAttribute("producer", producerId);
        model.addAttribute("amountOptions", amountOptions);
        return STOCK_PAGE;
    }
}
