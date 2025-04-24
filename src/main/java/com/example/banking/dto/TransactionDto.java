package com.example.banking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {

    private String accountNumber;
    private BigDecimal amount;
    private Integer type;
    private String transferAccount;
    private String description;
}
