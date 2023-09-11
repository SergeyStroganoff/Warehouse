package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.exception.DataVerificationException;

import java.util.List;
import java.util.Map;

public interface DataVerification {
    boolean verify(Map<Integer, List<String>> exelRowMap) throws DataVerificationException;
}
