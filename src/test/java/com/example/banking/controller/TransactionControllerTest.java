package com.example.banking.controller;

import com.example.banking.dto.TransactionDto;
import com.example.banking.dto.TransactionResponse;
import com.example.banking.enums.TransactionTypeEnum;
import com.example.banking.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private TransactionDto transactionDto;
    private TransactionResponse transactionResponse;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();

        transactionDto = new TransactionDto();
        transactionDto.setAccountNumber("Mike");
        transactionDto.setAmount(BigDecimal.valueOf(100.50));
        transactionDto.setType(TransactionTypeEnum.DEPOSIT.getCode());
        transactionDto.setDescription("Monthly salary");

        transactionResponse = new TransactionResponse();
        transactionResponse.setId(transactionId);
        transactionResponse.setAccountNumber("Mike");
        transactionResponse.setAmount(BigDecimal.valueOf(100.50));
        transactionResponse.setType(TransactionTypeEnum.TRANSFER.getCode());
        transactionResponse.setTransferAccount("Jessica");
        transactionResponse.setDescription("Give salary");
    }

    @Test
    void createTransaction_Success() {
        when(transactionService.createTransaction(any(TransactionDto.class))).thenReturn(transactionResponse);

        ResponseEntity<TransactionResponse> response =
                transactionController.createTransaction(transactionDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(transactionId, response.getBody().getId());
    }

    @Test
    void getTransactionById_Success() {
        when(transactionService.getTransactionById(transactionId)).thenReturn(transactionResponse);

        ResponseEntity<TransactionResponse> response =
                transactionController.getTransactionById(transactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(transactionId, response.getBody().getId());
    }

}
