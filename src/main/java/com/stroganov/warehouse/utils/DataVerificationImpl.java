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
    public boolean verify(Map<Integer, List<Object>> exelRowMap) throws DataVerificationException {
        if (exelRowMap == null) {
            throw new RuntimeException("Method parameter can not be null");
        }
        if (exelRowMap.isEmpty()) {
            String errorMessage = String.format("Method parameter can not be null. %s", this.getClass().getName());
            logger.error(errorMessage);
            throw new DataVerificationException("Verification error: Data set is empty");
        }

        List<String> cellSizeErrorList = new ArrayList<>();
        for (Map.Entry<Integer, List<Object>> entry : exelRowMap.entrySet()) {
            List<Object> objectList = entry.getValue();
            if (objectList.size() != 11) {
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("Parameters count incorrect, check row: ").append(entry.getKey());
                logger.debug(errorMessage.toString());
                throw new DataVerificationException(errorMessage.toString());
            }
            String firstCell = (String) objectList.get(0);
            if (firstCell.equals("Article")) {
                continue;
            }
            if (objectList.get(0).toString().length() > 12 ||
                    objectList.get(1).toString().length() > 10 ||
                    objectList.get(2).toString().length() > 10 ||
                    objectList.get(3).toString().length() > 10 ||
                    objectList.get(4).toString().length() > 250 ||
                    objectList.get(5).toString().length() > 10 ||
                    objectList.get(6).toString().length() > 35 ||
                    objectList.get(7).toString().length() > 20 ||
                    objectList.get(8).toString().length() > 255 ||
                    objectList.get(9).toString().length() > 18 ||
                    objectList.get(10).toString().length() > 18) {
                cellSizeErrorList.add(entry.getKey().toString());
            }

            if (!cellSizeErrorList.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("Parameter sizes  incorrect, check rows: ");
                cellSizeErrorList.forEach(s -> errorMessage.append(s).append(","));
                logger.debug(errorMessage.toString());
                throw new DataVerificationException(errorMessage.toString());
            }
        }
        return Boolean.TRUE;
    }
}
