package com.example.banking.exception;

public class TransactionNotFoundException  extends RuntimeException{
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
