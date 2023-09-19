package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.dto.transaction.DeliveryParserOptions;
import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
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
import java.util.Set;

import static com.stroganov.warehouse.controller.ProductLineController.NOTIFICATION;
import static com.stroganov.warehouse.controller.ProductLineController.SAVING_ERROR_MESSAGE;

@Controller
public class TransactionController {
    public static final String UPLOAD_DELIVERY_FORM_ADDRESS = "upload-delivery-form";
    @Autowired
    private Logger logger;

    @Autowired
    private StorageService storageService;


    @GetMapping("/upload/delivery-form")
    public String showRegisterForm(Model model) {
        model.addAttribute("deliveryParserOptions", DeliveryParserOptions.values());
        return UPLOAD_DELIVERY_FORM_ADDRESS;
    }

    @PostMapping("/upload/delivery")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("selectedOption") String selectedOption, Model model) {
        Path fileUploadedPath;
        Notification notification;
        Set<ExelTransactionRowDTO> exelTransactionRowSet;
        try {
            fileUploadedPath = storageService.store(file);
        } catch (StorageException e) {
            logger.error("Error during saving file: " + file.getOriginalFilename(), e);
            notification = new Notification("Error", SAVING_ERROR_MESSAGE + e.getMessage());
            model.addAttribute(NOTIFICATION, notification);
            return UPLOAD_DELIVERY_FORM_ADDRESS;
        }


        //  try {
        //      exelTransactionRowSet = batchTransactionParser.parseExelFile(fileUploadedPath);
        //  } catch (FileParsingException e) {
        //      logger.error("File parsing error: " + fileUploadedPath, e);
        //      notification = new Notification("Error", e.getMessage());
        //      model.addAttribute(NOTIFICATION, notification);
        //      return UPLOAD_DELIVERY_FORM_ADDRESS;
        //  }
        //   transactionService.saveAllUnique(exelTransactionRowSet); TODO  transaction method;
        notification = new Notification("Success", "You successfully uploaded " + file.getOriginalFilename() + "!");
        model.addAttribute(NOTIFICATION, notification);
        return UPLOAD_DELIVERY_FORM_ADDRESS;
    }
}
