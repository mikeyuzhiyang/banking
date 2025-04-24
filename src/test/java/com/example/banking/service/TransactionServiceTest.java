package com.example.banking.service;

import com.example.banking.dto.TransactionDto;
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
        transaction.setType(TransactionTypeEnum.TRANSFER.getCode());
        transaction.setTransferAccount("Jessica");
        transaction.setDescription("Give salary");
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
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void getTransactionById_NotFound() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransactionById(transactionId);
        });
    }

}
