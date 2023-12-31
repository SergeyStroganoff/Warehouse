package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.domain.model.service.Notification;
import com.stroganov.warehouse.exception.FileParsingException;
import com.stroganov.warehouse.exception.StorageException;
import com.stroganov.warehouse.service.DataStorageHandler;
import com.stroganov.warehouse.service.StorageService;
import com.stroganov.warehouse.service.item.ItemService;
import com.stroganov.warehouse.service.warehouse.StockService;
import com.stroganov.warehouse.utils.parser.DataParser;
import com.stroganov.warehouse.utils.verifier.DataVerifier;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;


@Controller
public class ProductLineController {
    public static final String NOTIFICATION = "notification";
    public static final String SAVING_ERROR_MESSAGE = "Failed unload file: ";
    public static final String UPLOAD_FILE_FORM = "upload-file-form";
    @Autowired
    private Logger logger;
    @Autowired
    private StorageService storageService;

    @Autowired
    private DataStorageHandler<Item> dataStorageHandler;

    @Autowired
    private ItemService itemService;

    @Autowired
    StockService stockService;

    @Autowired
    @Qualifier("itemVerifierImpl")
    private DataVerifier itemVerifierImpl;

    @Autowired
    @Qualifier("dataItemParserImpl")
    public DataParser<Item> itemDataParser;

    @GetMapping("/upload-product-line")
    public String showRegisterForm() {
        return UPLOAD_FILE_FORM;
    }

    @PostMapping("/upload/products")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        Path fileUploadedPath = null;
        Notification notification;
        Set<Item> itemSet;
        try {
            fileUploadedPath = storageService.store(file);
            itemSet = dataStorageHandler.parseExelFile(fileUploadedPath, itemDataParser, itemVerifierImpl);
            List<Item> savedItems = itemService.saveAllUnique(itemSet);
            if (!savedItems.isEmpty()) {
                stockService.stockInitialisation(savedItems, 0);
            }
            notification = new Notification("Success", String.format("You've uploaded: %s items from: %s", savedItems.size(), file.getOriginalFilename()));
        } catch (StorageException e) {
            logger.error("Error during saving file: " + file.getOriginalFilename(), e);
            notification = new Notification("Error", SAVING_ERROR_MESSAGE + e.getMessage());
        } catch (FileParsingException e) {
            logger.error("File parsing error: " + fileUploadedPath, e);
            notification = new Notification("Error", e.getMessage());
        }
        try {
            storageService.delete(fileUploadedPath);
        } catch (StorageException e) {
            logger.error(e.getMessage());
        }
        model.addAttribute(NOTIFICATION, notification);
        return UPLOAD_FILE_FORM;
    }
}
