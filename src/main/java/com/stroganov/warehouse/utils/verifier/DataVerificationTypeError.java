package com.stroganov.warehouse.utils.verifier;

import lombok.Getter;

@Getter
public enum DataVerificationTypeError {

    CELL_SIZE_ERROR("Parameter sizes  incorrect, check rows: "),
    CELL_FORMAT_ERROR("Format incorrect, check rows: ");

    private final String message;

    DataVerificationTypeError(String message) {
        this.message = message;
    }
}
