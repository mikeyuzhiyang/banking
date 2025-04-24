package com.example.banking.service;

import com.example.banking.dto.TransactionDto;
import com.example.banking.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionDto transactionDto);

    TransactionResponse getTransactionById(UUID id);

    List<TransactionResponse> getAllTransactions(int page, int size);

    List<TransactionResponse> getTransactionsByAccountNumber(String accountNumber, int page, int size);

    TransactionResponse updateTransaction(UUID id, TransactionDto transactionDto);

    void deleteTransaction(UUID id);

}
