package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.dto.transaction.DeliveryParserOptions;
import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.domain.model.service.Notification;
import com.stroganov.warehouse.domain.model.transaction.TransactionType;
import com.stroganov.warehouse.exception.FileParsingException;
import com.stroganov.warehouse.exception.StorageException;
import com.stroganov.warehouse.exception.TransactionServiceException;
import com.stroganov.warehouse.service.DataStorageHandler;
import com.stroganov.warehouse.service.StorageService;
import com.stroganov.warehouse.service.transaction.TransactionService;
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

    @Autowired
    @Qualifier("transactionListVerifierImpl")
    private DataVerifier transactionListVerifierImpl;

    @Autowired
    @Qualifier("transactionParserImpl")
    private DataParser<ExelTransactionRowDTO> exelTransactionRowDTODataParser;

    @Autowired
    private DataStorageHandler<ExelTransactionRowDTO> dataStorageHandler;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/upload/delivery-form")
    public String showRegisterForm(Model model) {
        model.addAttribute("deliveryParserOptions", DeliveryParserOptions.values());
        return UPLOAD_DELIVERY_FORM_ADDRESS;
    }

    @PostMapping("/upload/delivery")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("selectedOption") String selectedOption, Model model) {
        Path fileUploadedPath = null;
        Notification notification;
        Set<ExelTransactionRowDTO> exelTransactionRowSet;
        try {
            fileUploadedPath = storageService.store(file);
            exelTransactionRowSet = dataStorageHandler.parseExelFile(fileUploadedPath, exelTransactionRowDTODataParser, transactionListVerifierImpl);
            transactionService.doTransaction(exelTransactionRowSet, TransactionType.SUPPLY); // todo
        } catch (StorageException e) {
            logger.error("Error during saving file: " + file.getOriginalFilename(), e);
            notification = new Notification("Error", SAVING_ERROR_MESSAGE + e.getMessage());
            model.addAttribute(NOTIFICATION, notification);
            return UPLOAD_DELIVERY_FORM_ADDRESS;
        } catch (FileParsingException e) {
            logger.debug("File parsing error: " + fileUploadedPath, e);
            notification = new Notification("Error", e.getMessage());
            model.addAttribute(NOTIFICATION, notification);
            return UPLOAD_DELIVERY_FORM_ADDRESS;
        } catch (TransactionServiceException e) {
            logger.debug("Transaction error: " + e.getMessage());
            notification = new Notification("Transaction unsuccessfully", e.getMessage() + " Fix list or add new Items in Warehouse");
            model.addAttribute(NOTIFICATION, notification);
            return UPLOAD_DELIVERY_FORM_ADDRESS;
        }
        notification = new Notification("Success", "You successfully uploaded " + file.getOriginalFilename() + "!");
        model.addAttribute(NOTIFICATION, notification);
        return UPLOAD_DELIVERY_FORM_ADDRESS;
    }
}
