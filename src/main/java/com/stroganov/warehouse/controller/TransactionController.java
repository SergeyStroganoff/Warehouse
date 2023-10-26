package com.stroganov.warehouse.controller;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.domain.dto.transaction.FileTypeOptions;
import com.stroganov.warehouse.domain.model.service.Notification;
import com.stroganov.warehouse.domain.model.transaction.TransactionType;
import com.stroganov.warehouse.exception.FileParsingException;
import com.stroganov.warehouse.exception.StorageException;
import com.stroganov.warehouse.exception.TransactionServiceException;
import com.stroganov.warehouse.mangment.ParserManager;
import com.stroganov.warehouse.service.StorageService;
import com.stroganov.warehouse.service.transaction.TransactionService;
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
    public static final String UPLOAD_DELIVERY_FORM_ADDRESS = "upload-transaction-form";
    public static final String PARSER_OPTIONS = "parserOptions";
    public static final String OPERATION_TYPE = "operationType";
    @Autowired
    private Logger logger;
    @Autowired
    private StorageService storageService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ParserManager parserManager;

    @GetMapping("/upload/delivery-form")
    public String showDeliveryForm(Model model) {
        model.addAttribute(PARSER_OPTIONS, FileTypeOptions.values());
        model.addAttribute(OPERATION_TYPE, TransactionType.SUPPLY.toString());
        return UPLOAD_DELIVERY_FORM_ADDRESS;
    }

    @GetMapping("/upload/consumption-form")
    public String showConsumptionForm(Model model) {
        model.addAttribute(PARSER_OPTIONS, FileTypeOptions.values());
        model.addAttribute(OPERATION_TYPE, TransactionType.CONSUMPTION.toString());
        return UPLOAD_DELIVERY_FORM_ADDRESS;
    }

    @PostMapping("/upload/transaction")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("selectedOption") String selectedOption,
                                   @RequestParam("transactionType") String transactionType,
                                   Model model) {
        Path fileUploadedPath = null;
        int recordedRows;
        Notification notification;
        Set<ExelTransactionRowDTO> exelTransactionRowSet;
        TransactionType type = TransactionType.valueOf(transactionType);
        model.addAttribute(PARSER_OPTIONS, FileTypeOptions.values());
        model.addAttribute(OPERATION_TYPE, type.toString());
        try {
            fileUploadedPath = storageService.store(file);
            exelTransactionRowSet = parserManager.parseFile(fileUploadedPath, FileTypeOptions.valueOf(selectedOption));
            recordedRows = transactionService.doTransaction(exelTransactionRowSet, type);
            storageService.delete(fileUploadedPath);
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
        String message = String.format("You successfully uploaded %s file. %n Recorded: %d rows", file.getOriginalFilename(), recordedRows);
        notification = new Notification("Success", message);
        model.addAttribute(NOTIFICATION, notification);
        return UPLOAD_DELIVERY_FORM_ADDRESS;
    }
}
