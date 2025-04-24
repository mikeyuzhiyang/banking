package com.example.banking.exception;

public class DuplicateTransactionException extends RuntimeException{
    public DuplicateTransactionException(String message) {
        super(message);
    }
}
