package com.example.banking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponse {

    private UUID id;
    private String accountNumber;
    private BigDecimal amount;
    private Integer type;
    private String transferAccount;
    private LocalDateTime timestamp;
    private String description;

}
