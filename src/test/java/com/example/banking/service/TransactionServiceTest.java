package com.example.banking.service;

import com.example.banking.dto.TransactionDto;
import com.example.banking.dto.TransactionResponse;
import com.example.banking.enums.TransactionTypeEnum;
import com.example.banking.exception.TransactionNotFoundException;
import com.example.banking.model.Transaction;
import com.example.banking.repository.TransactionRepository;
import com.example.banking.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {


    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionDto transactionDto;
    private Transaction transaction;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();

        transactionDto = new TransactionDto();
        transactionDto.setAccountNumber("Mike");
        transactionDto.setAmount(BigDecimal.valueOf(100.50));
        transactionDto.setType(TransactionTypeEnum.DEPOSIT.getCode());
        transactionDto.setDescription("Monthly salary");

        transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAccountNumber("Mike");
        transaction.setAmount(BigDecimal.valueOf(100.50));
        transaction.setType(TransactionTypeEnum.DEPOSIT.getCode());
        transaction.setDescription("Monthly salary");
    }

    @Test
    void createTransaction_Success() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        var response = transactionService.createTransaction(transactionDto);

        assertNotNull(response);
        assertEquals(transactionId, response.getId());
        assertEquals("Mike", response.getAccountNumber());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getTransactionById_Success() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        var response = transactionService.getTransactionById(transactionId);

        assertNotNull(response);
        assertEquals(transactionId, response.getId());
    }

    @Test
    void getTransactionById_NotFound() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransactionById(transactionId);
        });
    }

    @Test
    void getAllTransaction_Success() {
        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));
        List<TransactionResponse> responseList = transactionService.getAllTransactions(0,1);
        assertNotNull(responseList);
        assertEquals(transactionId, responseList.getFirst().getId());



    }

    @Test
    void deleteTransaction_Fail() {
        when(transactionRepository.existsById(any())).thenReturn(Boolean.FALSE);
        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.deleteTransaction(transactionId);
        });
    }


    @Test
    void updateTransaction_Success() {
        when(transactionRepository.findById(any())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse response = transactionService.updateTransaction(transactionId, transactionDto);
        assertNotNull(response);
        assertEquals(transactionId, response.getId());
    }

    @Test
    void getTransactionsByAccountNumber_Success() {
        when(transactionRepository.findByAccountNumber(any(), anyInt(), anyInt())).thenReturn(Collections.singletonList(transaction));
        List<TransactionResponse> responseList = transactionService.getTransactionsByAccountNumber("Mike", 0, 1);
        assertNotNull(responseList);
        assertEquals(transactionId, responseList.getFirst().getId());
    }
}
