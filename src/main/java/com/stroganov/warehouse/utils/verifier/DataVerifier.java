package com.stroganov.warehouse.utils.verifier;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public interface DataVerifier {
    boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException;

    public static final String ARTICLE = "Article";

    static <K, V> boolean notNullOrEmptyCheck(Map<K, V> exelRowMap, Logger logger) throws DataVerificationException {
        if (exelRowMap == null || exelRowMap.isEmpty()) {
            String errorMessage = String.format("Method parameter can not be null or empty. %s", DataVerifier.class);
            logger.error(errorMessage);
            throw new DataVerificationException("Verification error: Data set is empty or Null");
        }
        return Boolean.TRUE;
    }

    static boolean CheckParsingErrors(List<String> cellSizeErrorList, List<String> cellFormatErrorList, Logger logger) throws DataVerificationException {
        if (!cellSizeErrorList.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Parameter sizes  incorrect, check rows: ");
            cellSizeErrorList.forEach(s -> errorMessage.append(s).append(","));
            logger.debug(errorMessage.toString());
            throw new DataVerificationException(errorMessage.toString());
        }
        if (!cellFormatErrorList.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Format incorrect, check rows: ");
            cellFormatErrorList.forEach(s -> errorMessage.append(s).append(","));
            logger.debug(errorMessage.toString());
            throw new DataVerificationException(errorMessage.toString());
        }
        return Boolean.TRUE;
    }
}
