package com.example.banking.repository;

import com.example.banking.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {

    private final Map<UUID, Transaction> transactions = new ConcurrentHashMap<>();

    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    public Optional<Transaction> findById(UUID id) {
        return Optional.ofNullable(transactions.get(id));
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    public void deleteById(UUID id) {
        transactions.remove(id);
    }

    public boolean existsById(UUID id) {
        return transactions.containsKey(id);
    }

    public List<Transaction> findByAccountNumber(String accountNumber, int page, int size) {
        return transactions.values().stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

}
