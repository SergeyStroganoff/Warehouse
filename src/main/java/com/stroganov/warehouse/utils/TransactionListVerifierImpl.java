package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TransactionListVerifierImpl implements DataVerifier {
    @Override
    public boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException {
        return false;
    }
}
