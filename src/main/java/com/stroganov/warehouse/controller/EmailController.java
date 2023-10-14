package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.model.service.Notification;
import com.stroganov.warehouse.service.EmailService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private Logger logger;

    @Value("${email.address}")
    String emailAddressTo;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String subject, @RequestParam String body, @RequestParam String email, Model model) {
        if (body.length() > 350) {
            return "redirect:/support";
        }
        body = String.format("\"%s\" %nSender: %s",body, email);
        emailService.sendEmail(emailAddressTo, subject, body);
        Notification notification = new Notification("Success", "You've sent message to us! ");
        model.addAttribute("notification", notification);
        return "main";
    }
}

