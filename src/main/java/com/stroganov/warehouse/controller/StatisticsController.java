package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.model.item.Manufacture;
import com.stroganov.warehouse.service.item.ManufactureService;
import com.stroganov.warehouse.service.warehouse.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StatisticsController {

    @Autowired
    private ManufactureService manufactureService;

    @Autowired
    private StockService stockService;

    @GetMapping("/statistics-form")
    public String getAllStockPage(Model model) {
        List<Manufacture> manufactureList = manufactureService.findAllManufacture();

        return "/statistics";
    }


}
