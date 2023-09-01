package com.stroganov.warehouse.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyWEBErrorController implements ErrorController {

    public static final String ERROR_404_MESSAGE = "We couldn't find the page you were looking for.";
    private static final String ERROR_500_MESSAGE = "Internally Server Error.";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("ErrorMessage", ERROR_404_MESSAGE);
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("ErrorMessage", ERROR_500_MESSAGE);
            }
        }
        return "error";
    }
}
