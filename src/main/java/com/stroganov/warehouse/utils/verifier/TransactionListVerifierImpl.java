package com.stroganov.warehouse.utils.verifier;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TransactionListVerifierImpl implements DataVerifier {

    @Autowired
    public Logger logger;

    @Override
    public boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException {
        DataVerifier.notNullOrEmptyCheck(exelRowMap, logger);
        List<String> cellSizeErrorList = new ArrayList<>();
        List<String> priceErrorList = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : exelRowMap.entrySet()) {
            List<String> cellValueList = entry.getValue();
            if (cellValueList.size() != 4) {
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
                    cellValueList.get(1).length() > 20 ||
                    cellValueList.get(2).length() > 10 ||
                    cellValueList.get(3).length() > 7) {
                cellSizeErrorList.add(entry.getKey().toString());
            }
            if (!cellValueList.get(3).matches("\\d{1,7}")) {
                priceErrorList.add(entry.getKey().toString());
            }
        }
        return DataVerifier.CheckParsingErrors(cellSizeErrorList, priceErrorList, logger);
    }
}









