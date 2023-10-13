package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.service.warehouse.StatisticsService;
import com.stroganov.warehouse.service.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/wsx")
    public String getAllStockPage() {
        return "/upload-file-form";
    }

    @GetMapping("/sample")
    public String getSimple() {
        System.out.println("Мы зашли в метод контроллера");
        return "/ddd";
    }
}
