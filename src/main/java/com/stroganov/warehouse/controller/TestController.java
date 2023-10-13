package com.stroganov.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String getTest() {
        System.out.println("Мы зашли в метод контроллера");
        return "/test";
    }
}
