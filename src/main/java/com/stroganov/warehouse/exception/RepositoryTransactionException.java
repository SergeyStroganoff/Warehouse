package com.stroganov.warehouse.exception;

public class RepositoryTransactionException extends Exception {

    public RepositoryTransactionException(String message) {
        super(message);
    }

    public RepositoryTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
