package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DataVerificationImpl implements DataVerification {

    @Autowired
    public Logger logger;

    @Override
    public boolean verify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException {
        if (exelRowMap == null) {
            throw new RuntimeException("Method parameter can not be null");
        }
        if (exelRowMap.isEmpty()) {
            String errorMessage = String.format("Method parameter can not be null. %s", this.getClass().getName());
            logger.error(errorMessage);
            throw new DataVerificationException("Verification error: Data set is empty");
        }

        List<String> cellSizeErrorList = new ArrayList<>();
        List<String> priceErrorList = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : exelRowMap.entrySet()) {
            List<String> cellValueList = entry.getValue();
            if (cellValueList.size() != 11) {
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("Parameters count incorrect, check row: ").append(entry.getKey());
                logger.debug(errorMessage.toString());
                throw new DataVerificationException(errorMessage.toString());
            }
            String firstCell = cellValueList.get(0);
            if (firstCell.equals("Article")) {
                continue;
            }
            if (cellValueList.get(0).length() > 12 ||
                    cellValueList.get(1).length() > 10 ||
                    cellValueList.get(2).length() > 10 ||
                    cellValueList.get(3).length() > 10 ||
                    cellValueList.get(4).length() > 250 ||
                    cellValueList.get(5).length() > 10 ||
                    cellValueList.get(6).length() > 35 ||
                    cellValueList.get(7).length() > 20 ||
                    cellValueList.get(8).length() > 255 ||
                    cellValueList.get(9).length() > 18 ||
                    cellValueList.get(10).length() > 18) {
                cellSizeErrorList.add(entry.getKey().toString());
            }
            if (!cellValueList.get(9).matches("\\$?\\w+.?\\w{0,2}") || !cellValueList.get(10).matches("\\$?\\w+.?\\w{0,2}")) {
                priceErrorList.add(entry.getKey().toString());
            }
        }
        if (!cellSizeErrorList.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Parameter sizes  incorrect, check rows: ");
            cellSizeErrorList.forEach(s -> errorMessage.append(s).append(","));
            logger.debug(errorMessage.toString());
            throw new DataVerificationException(errorMessage.toString());
        }
        if (!priceErrorList.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Cost and sale price incorrect, check rows: ");
            priceErrorList.forEach(s -> errorMessage.append(s).append(","));
            logger.debug(errorMessage.toString());
            throw new DataVerificationException(errorMessage.toString());
        }
        return Boolean.TRUE;
    }
}
