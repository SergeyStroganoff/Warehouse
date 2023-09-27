package com.stroganov.warehouse.utils.verifier;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemVerifierImpl implements DataVerifier {
    @Autowired
    public Logger logger;
    @Override
    public boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException {
        RowChecker rowChecker = (x, r) -> {
            Map<String, DataVerificationTypeError> typeErrorHashMap = new HashMap<>();
            if (x.get(0).length() > 12 ||
                    x.get(1).length() > 10 ||
                    x.get(2).length() > 10 ||
                    x.get(3).length() > 10 ||
                    x.get(4).length() > 250 ||
                    x.get(5).length() > 10 ||
                    x.get(6).length() > 35 ||
                    x.get(7).length() > 20 ||
                    x.get(8).length() > 255 ||
                    x.get(9).length() > 18 ||
                    x.get(10).length() > 18) {
                typeErrorHashMap.put(String.valueOf(r), DataVerificationTypeError.CELL_SIZE_ERROR);
            }
            if (!x.get(9).matches("\\$?\\w+.?\\w{0,2}") || !x.get(10).matches("\\$?\\w+.?\\w{0,2}")) {
                typeErrorHashMap.put(String.valueOf(r), DataVerificationTypeError.CELL_FORMAT_ERROR);
            }
            return typeErrorHashMap;
        };
        return DataVerifier.commonDataVerify(logger, exelRowMap, rowChecker, 11);
    }
}

