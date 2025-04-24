package com.example.banking.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
public class Transaction {

    private UUID id;
    private String accountNumber;
    private BigDecimal amount;
    private Integer type; // DEPOSIT, WITHDRAWAL, TRANSFER
    private String transferAccount;
    private LocalDateTime timestamp;
    private String description;

    // Constructors, getters, setters, equals, hashCode
    public Transaction() {
        this.id = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
    }

    // Override equals and hashCode based on id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
