package com.example.banking.controller;

import com.example.banking.dto.TransactionDto;
import com.example.banking.dto.TransactionResponse;
import com.example.banking.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        TransactionResponse response = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id) {
        TransactionResponse response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<TransactionResponse> responses = transactionService.getAllTransactions(page, size);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountNumber(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<TransactionResponse> responses = transactionService.getTransactionsByAccountNumber(accountNumber, page, size);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/update")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @Valid @RequestBody TransactionDto transactionDto) {
        TransactionResponse response = transactionService.updateTransaction(transactionDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

}
