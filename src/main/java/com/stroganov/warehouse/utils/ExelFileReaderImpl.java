package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.NoSuchSheetException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


@Component
public class ExelFileReaderImpl implements ExelFileReader {
    private static final String UNDEFINED = "undefined";

    @Autowired
    public Logger logger;

    @Override
    public Map<Integer, List<String>> readExelTable(String file, String sheetName) throws IOException, FileExtensionError, NoSuchSheetException {
        Map<Integer, List<String>> exelTable;
        try (Workbook workbook = loadWorkbook(file)) {
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                if (sheet.getSheetName().equals(sheetName)) {
                    exelTable = processSheet(sheet);
                    return exelTable;
                }
            }
        }
        String message = String.format("Log: Sheet %s hasn't found in this book", sheetName);
        logger.error(message);
        throw new NoSuchSheetException(message);
    }


    private Workbook loadWorkbook(String filename) throws IOException, FileExtensionError {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            return switch (extension) {
                case "xls" -> new HSSFWorkbook(fileInputStream);
                case "xlsx" -> new XSSFWorkbook(fileInputStream);
                default -> throw new FileExtensionError("Unknown Excel file extension: " + extension);
            };
        }
    }

    private HashMap<Integer, List<String>> processSheet(Sheet sheet) {
        HashMap<Integer, List<String>> data = new HashMap<>();
        Iterator<Row> iterator = sheet.rowIterator();
        for (int rowIndex = 0; iterator.hasNext(); rowIndex++) {
            Row row = iterator.next();
            processRow(data, rowIndex, row);
        }
        return data;
    }

    private void processRow(HashMap<Integer, List<String>> data, int rowIndex, Row row) {
        data.put(rowIndex, new ArrayList<>());
        for (Cell cell : row) {
            processCell(cell, data.get(rowIndex));
        }
    }

    private void processCell(Cell cell, List<String> dataRow) {
        switch (cell.getCellType()) {
            case STRING -> dataRow.add(cell.getStringCellValue());
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    dataRow.add(cell.getLocalDateTimeCellValue().toString());
                } else {
                    dataRow.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
                }
            }
            case BOOLEAN -> dataRow.add(Boolean.toString(cell.getBooleanCellValue()));
            case FORMULA -> dataRow.add(cell.getCellFormula());
            default -> dataRow.add(UNDEFINED);
        }
    }
}
