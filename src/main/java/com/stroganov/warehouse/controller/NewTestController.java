package com.stroganov.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewTestController {

    @GetMapping("/support")
    public String getTestPAge(){
        return "test";
    }
}
