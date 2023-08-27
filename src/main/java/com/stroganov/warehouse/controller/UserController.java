package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.dto.user.UserRegistrationDTO;
import com.stroganov.warehouse.domain.model.service.Notification;
import com.stroganov.warehouse.domain.model.user.Role;
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

    public static final String ADMIN_REGISTRATION_MESSAGE = "Now you can Log in with name and password";
    public static final String USER_REGISTRATION_MESSAGE = "Now new user can Log in with his name and password";
    public static final String TITLE = "Registration successful";
    private static final String TITLE_ERROR = "Registration fail";
    private static final String ERROR_MESSAGE = "Error during saving new user, please connect with revise desk";
    public static final String REGISTRATION = "admin-registration";
    public static final String NEW_USER_FORM = "new-user-form";
    public static final String GLOBAL = "global";
    public static final String USER_WITH_SUCH_NAME_IS_PRESENT_MESSAGE = "User with such name is present, use another name.";
    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationService userRegistrationService;


    @GetMapping("/admin-registration")
    public String showRegisterForm(UserRegistrationDTO userRegistrationDTO) {
        return REGISTRATION;
    }

    @PostMapping("/registration-action")
    public String registerAdmin(@Valid @ModelAttribute("userRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) { // validation
            return REGISTRATION;
        }
        if (userService.findUserByName(userRegistrationDTO.getUserName()).isPresent()) {
            bindingResult.addError(new ObjectError(GLOBAL, USER_WITH_SUCH_NAME_IS_PRESENT_MESSAGE));
            return REGISTRATION;
        }
        Notification notification;
        try {
            userRegistrationService.registerNewUser(userRegistrationDTO);
            notification = new Notification(TITLE, ADMIN_REGISTRATION_MESSAGE);
        } catch (RepositoryTransactionException e) {
            notification = new Notification(TITLE_ERROR, ERROR_MESSAGE);
        }
        model.addAttribute("notification", notification);
        return "hello";
    }

    @GetMapping("/call-user-form")
    public String showAddNewUserForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("availableRoles", Role.values());
        return NEW_USER_FORM;
    }

    @PostMapping("/user-registration")
    public String registerUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                               BindingResult bindingResult, Model model) {
        model.addAttribute("availableRoles", Role.values());
        if (bindingResult.hasErrors()) { // validation
            return NEW_USER_FORM;
        }
        if (userService.findUserByName(userDTO.getUserName()).isPresent()) {
            bindingResult.addError(new ObjectError(GLOBAL, USER_WITH_SUCH_NAME_IS_PRESENT_MESSAGE));
            return NEW_USER_FORM;
        }

        userDTO.setEnabled(Boolean.TRUE);
        Notification notification;
        try {
            userService.save(userDTO);
            notification = new Notification(TITLE, USER_REGISTRATION_MESSAGE);
        } catch (RepositoryTransactionException e) {
            notification = new Notification(TITLE_ERROR, ERROR_MESSAGE);
        }
        model.addAttribute("notification", notification);
        return "main";
    }
}
