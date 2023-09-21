package com.stroganov.warehouse.utils.verifier;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ItemVerifierImpl implements DataVerifier {
    @Autowired
    public Logger logger;
    public static final String ARTICLE = "Article";

    @Override
    public boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException {
        DataVerifier.notNullOrEmptyCheck(exelRowMap, logger);
        List<String> cellSizeErrorList = new ArrayList<>();
        List<String> cellFormatErrorList = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : exelRowMap.entrySet()) {
            List<String> cellValueList = entry.getValue();
            if (cellValueList.size() != 11) {
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("Parameters count incorrect, check row: ").append(entry.getKey());
                logger.debug(errorMessage.toString());
                throw new DataVerificationException(errorMessage.toString());
            }
            String firstCell = cellValueList.get(0);
            if (ARTICLE.equalsIgnoreCase(firstCell)) {
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
                cellFormatErrorList.add(entry.getKey().toString());
            }
        }
        return DataVerifier.CheckParsingErrors(cellSizeErrorList, cellFormatErrorList, logger);
    }
}

