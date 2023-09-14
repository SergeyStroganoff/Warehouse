package com.stroganov.warehouse.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {
    @Autowired
    private Logger logger;

    //take into account the delivery

    @GetMapping("/upload/deliver")
    public String showRegisterForm(Model model) {
        return "upload-delivery-form";
    }

}
