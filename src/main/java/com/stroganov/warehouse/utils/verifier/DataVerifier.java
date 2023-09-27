package com.stroganov.warehouse.utils.verifier;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DataVerifier {

    public static final String ARTICLE = "Article";

    boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException;

    static boolean commonDataVerify(Logger logger, Map<Integer, List<String>> exelRowMap, RowChecker rowChecker, int rowLength) throws DataVerificationException {
        DataVerifier.notNullOrEmptyCheck(exelRowMap, logger);
        Map<String, DataVerificationTypeError> verificationErrors = new HashMap<>();
        int rowNumber = 0;
        for (Map.Entry<Integer, List<String>> entry : exelRowMap.entrySet()) {
            rowNumber++;
            List<String> cellValueList = entry.getValue();
            if (cellValueList.size() != rowLength) {
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("Parameters count incorrect, check row: ").append(entry.getKey());
                logger.debug(errorMessage.toString());
                throw new DataVerificationException(errorMessage.toString());
            }
            String firstCell = cellValueList.get(0);
            if (ARTICLE.equalsIgnoreCase(firstCell)) {
                continue;
            }
            verificationErrors = rowChecker.checkRow(cellValueList,rowNumber);
        }
        return DataVerifier.CheckParsingErrors(verificationErrors, logger);
    }


    static <K, V> boolean notNullOrEmptyCheck(Map<K, V> exelRowMap, Logger logger) throws DataVerificationException {
        if (exelRowMap == null || exelRowMap.isEmpty()) {
            String errorMessage = String.format("Method parameter can not be null or empty. %s", DataVerifier.class);
            logger.error(errorMessage);
            throw new DataVerificationException("Verification error: Data set is empty or Null");
        }
        return Boolean.TRUE;
    }

    static boolean CheckParsingErrors(Map<String, DataVerificationTypeError> errorMap, Logger logger) throws DataVerificationException {
        if (!errorMap.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMap.forEach((k, v) -> errorMessage.append(v.getMessage()).append(k).append("\n"));
            logger.debug(errorMessage.toString());
            throw new DataVerificationException(errorMessage.toString());
        }
        return Boolean.TRUE;
    }
}
