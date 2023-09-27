package com.stroganov.warehouse.utils.verifier;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface RowChecker {
    Map<String, DataVerificationTypeError> checkRow(List<String> cellValueList, int rowNumber);
}
