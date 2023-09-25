package com.stroganov.warehouse.exception;

public class TransactionServiceException extends Exception {
    public TransactionServiceException(String message) {
        super(message);
    }

    public TransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
