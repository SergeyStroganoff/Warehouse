package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.exception.DataVerificationException;

import java.util.List;
import java.util.Map;

public interface DataVerifier {
    boolean dataVerify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException;
}
