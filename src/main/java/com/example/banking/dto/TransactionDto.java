package com.example.banking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto {

    private UUID id;
    private String accountNumber;
    private BigDecimal amount;
    private Integer type;
    private String transferAccount;
    private String description;
}
