package com.stroganov.warehouse.utils.verifier;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NordicTransactionListVerifierImpl implements DataVerifier {

    @Autowired
    public Logger logger;

    @Override
    public boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException {
        RowChecker rowChecker = (x, r) -> {
            Map<String, DataVerificationTypeError> typeErrorHashMap = new HashMap<>();
            if (x.get(0).length() < 5 ||
                    x.get(0).length() > 12 ||
                    x.get(1).length() > 7) {
                typeErrorHashMap.put(String.valueOf(r), DataVerificationTypeError.CELL_SIZE_ERROR);
            }
            if (!x.get(1).matches("\\d{1,7}") || !x.get(0).matches("^(NWS|NGS|NES|NCS) .{1,12}$")) {
                typeErrorHashMap.put(String.valueOf(r), DataVerificationTypeError.CELL_FORMAT_ERROR);
            }


            return typeErrorHashMap;
        };
        return DataVerifier.commonDataVerify(logger, exelRowMap, rowChecker, 2);
    }
}
