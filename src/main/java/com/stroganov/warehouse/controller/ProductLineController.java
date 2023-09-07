package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.model.service.Notification;
import com.stroganov.warehouse.exception.StorageException;
import com.stroganov.warehouse.service.StorageService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


@Controller
public class ProductLineController {

    private final String ERROR_MESSAGE = "Failed unload file: ";

    @Autowired
    private Logger logger;

    @Autowired
    private StorageService storageService;

    @GetMapping("/upload-product-line")
    public String showRegisterForm(Model model) {
        return "upload-file-form";
    }

    @PostMapping("/upload-action")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        Path fileUploadedPath;
        Notification notification;
        try {
            fileUploadedPath = storageService.store(file);

        } catch (StorageException e) {
            logger.error("Error during saving file: " + file.getOriginalFilename(), e);
            notification = new Notification("Error", ERROR_MESSAGE + e.getMessage());
            model.addAttribute("notification", notification);
            return "upload-file-form";
        }
        notification = new Notification("Success", "You successfully uploaded " + file.getOriginalFilename() + "!");

        model.addAttribute("notification", notification);
        return "upload-file-form";
    }
}
