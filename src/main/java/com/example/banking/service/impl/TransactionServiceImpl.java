package com.example.banking.service.impl;

import com.example.banking.dto.TransactionDto;
import com.example.banking.dto.TransactionResponse;
import com.example.banking.exception.DuplicateTransactionException;
import com.example.banking.exception.TransactionNotFoundException;
import com.example.banking.model.Transaction;
import com.example.banking.repository.TransactionRepository;
import com.example.banking.service.TransactionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @CacheEvict(value = "transactions", allEntries = true)
    public TransactionResponse createTransaction(TransactionDto transactionDto) {
        Transaction transaction = convertToEntity(transactionDto);

        if (transactionRepository.existsById(transaction.getId())) {
            throw new DuplicateTransactionException("Transaction with this ID already exists");
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToResponse(savedTransaction);
    }

    @Override
    @Cacheable(value = "transaction", key = "#id")
    public TransactionResponse getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + id));
        return convertToResponse(transaction);
    }

    @Override
    @Cacheable(value = "transactions", key = "#page + '-' + #size")
    public List<TransactionResponse> getAllTransactions(int page, int size) {
        List<Transaction> transactions = transactionRepository.findAll()
                .stream()
                .skip((long) page * size)
                .limit(size)
                .toList();

        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "accountTransactions", key = "#accountNumber + '-' + #page + '-' + #size")
    public List<TransactionResponse> getTransactionsByAccountNumber(String accountNumber, int page, int size) {
        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber, page, size);
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"transaction", "transactions", "accountTransactions"}, allEntries = true)
    public TransactionResponse updateTransaction(TransactionDto transactionDto) {

        Transaction existingTransaction = transactionRepository.findById(transactionDto.getId())
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + transactionDto.getId()));

        Transaction updatedTransaction = convertToEntity(transactionDto);
        updatedTransaction.setId(transactionDto.getId());

        transactionRepository.save(updatedTransaction);
        return convertToResponse(updatedTransaction);
    }

    @Override
    @CacheEvict(value = {"transaction", "transactions", "accountTransactions"}, allEntries = true)
    public void deleteTransaction(UUID id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private Transaction convertToEntity(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(dto.getAccountNumber());
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setTransferAccount(dto.getTransferAccount());
        transaction.setDescription(dto.getDescription());
        return transaction;
    }

    private TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAccountNumber(transaction.getAccountNumber());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setTransferAccount(transaction.getTransferAccount());
        response.setTimestamp(transaction.getTimestamp());
        response.setDescription(transaction.getDescription());
        return response;
    }

}
