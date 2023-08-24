package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.dto.user.UserRegistrationDTO;
import com.stroganov.warehouse.domain.model.service.Notification;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import com.stroganov.warehouse.service.UserRegistrationService;
import com.stroganov.warehouse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    public static final String MESSAGE = "Now you can Log in with name and password";
    public static final String TITLE = "Registration successful";
    private static final String TITLE_ERROR = "Registration fail";
    private static final String ERROR_MESSAGE = "Error during saving new user, please connect with revise desk";
    public static final String REGISTRATION = "registration";
    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationService userRegistrationService;


    @GetMapping("/registration")
    public String showRegisterForm(UserRegistrationDTO userRegistrationDTO) {
        return REGISTRATION;
    }

    @PostMapping("/registration-action")
    public String registerUser(@Valid @ModelAttribute("userRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
                               BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) { // validation
            return REGISTRATION;
        }
        if (userService.findUserByName(userRegistrationDTO.getUserName()).isPresent()) {
            bindingResult.addError(new ObjectError("global", "User with such name is present, use another name."));
            return REGISTRATION;
        }
        Notification notification;
        try {
            userRegistrationService.registerNewUser(userRegistrationDTO);
            notification = new Notification(TITLE, MESSAGE);
        } catch (RepositoryTransactionException e) {
            notification = new Notification(TITLE_ERROR, ERROR_MESSAGE);
        }
        model.addAttribute("notification", notification);
        return "hello";
    }
}
